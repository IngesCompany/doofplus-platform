package com.ingescompany.doofplus.platform.subscriptions.application.internal.commandservices;

import com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway.PaymentGateway;
import com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway.PaymentResult;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.PaySubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.*;
import com.ingescompany.doofplus.platform.subscriptions.domain.repositories.PlanRepository;
import com.ingescompany.doofplus.platform.subscriptions.domain.repositories.SubscriptionRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaySubscriptionCommandServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private PaymentGateway paymentGateway;

    private SubscriptionCommandServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new SubscriptionCommandServiceImpl(subscriptionRepository, planRepository, paymentGateway);
    }

    private static Subscription subscription(SubscriptionStatus status) {
        return Subscription.reconstitute(1L, new OrganizationId(1L), 2L, BillingCycle.ANNUAL, status, true,
                new PaymentCard("Visa", "4242", "12/2030"), new BillingEmail("billing@andinos.com.pe"),
                null, null, List.of());
    }

    private static Plan plan() {
        return Plan.reconstitute(2L, new PlanKey("standard-lab"), "Standard Lab",
                new Money(new BigDecimal("49.90"), "USD"), new Money(new BigDecimal("499.00"), "USD"), 10, 5);
    }

    private static ApplicationError error(Result<Subscription, ApplicationError> result) {
        return ((Result.Failure<Subscription, ApplicationError>) result).error();
    }

    private void givenSubscriptionAndPlan(SubscriptionStatus status) {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription(status)));
        when(planRepository.findById(2L)).thenReturn(Optional.of(plan()));
        when(subscriptionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void anApprovedPaymentShouldActivateAPendingSubscription() {
        givenSubscriptionAndPlan(SubscriptionStatus.PENDING);
        when(paymentGateway.authorize(any(), eq("tok"))).thenReturn(PaymentResult.approved("993201", "Aprobado"));

        var result = service.handle(new PaySubscriptionCommand(1L, "tok"));

        var subscription = result.toOptional().orElseThrow();
        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
        var payment = subscription.getPayments().getFirst();
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.APPROVED);
        assertThat(payment.getGatewayTransactionId()).isEqualTo("993201");
        assertThat(payment.getInvoiceNumber()).isEqualTo("INV-1-1");
    }

    @Test
    void thePaymentShouldChargeThePriceOfTheBillingCycle() {
        givenSubscriptionAndPlan(SubscriptionStatus.PENDING);
        when(paymentGateway.authorize(any(), any())).thenReturn(PaymentResult.approved("993201", "Aprobado"));

        service.handle(new PaySubscriptionCommand(1L, "tok"));

        verify(paymentGateway).authorize(new Money(new BigDecimal("499.00"), "USD"), "tok");
    }

    @Test
    void aRejectedPaymentShouldBeRecordedAndReported() {
        givenSubscriptionAndPlan(SubscriptionStatus.PENDING);
        when(paymentGateway.authorize(any(), any())).thenReturn(PaymentResult.rejected("Operacion denegada"));

        var result = service.handle(new PaySubscriptionCommand(1L, "tok"));

        assertThat(error(result).code()).isEqualTo("BUSINESS_RULE_VIOLATION");
        assertThat(error(result).details()).isEqualTo("Operacion denegada");
        verify(subscriptionRepository).save(argThat(saved ->
                saved.getStatus() == SubscriptionStatus.PENDING
                        && saved.getPayments().getFirst().getStatus() == PaymentStatus.REJECTED));
    }

    @Test
    void anUnavailableGatewayShouldReturnServiceUnavailableWithoutRecordingThePayment() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription(SubscriptionStatus.PENDING)));
        when(planRepository.findById(2L)).thenReturn(Optional.of(plan()));
        when(paymentGateway.authorize(any(), any())).thenReturn(PaymentResult.unavailable("Payment gateway is not available"));

        var result = service.handle(new PaySubscriptionCommand(1L, "tok"));

        assertThat(error(result).code()).isEqualTo("SERVICE_UNAVAILABLE");
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void payShouldFailWhenTheSubscriptionDoesNotExist() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.empty());

        var result = service.handle(new PaySubscriptionCommand(1L, "tok"));

        assertThat(error(result).code()).isEqualTo("SUBSCRIPTION_NOT_FOUND");
        verifyNoInteractions(paymentGateway);
    }

    @Test
    void payShouldFailWhenTheSubscriptionIsCancelled() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription(SubscriptionStatus.CANCELLED)));

        var result = service.handle(new PaySubscriptionCommand(1L, "tok"));

        assertThat(error(result).code()).isEqualTo("BUSINESS_RULE_VIOLATION");
        verifyNoInteractions(paymentGateway);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void payShouldFailWhenThePlanDoesNotExist() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription(SubscriptionStatus.PENDING)));
        when(planRepository.findById(2L)).thenReturn(Optional.empty());

        var result = service.handle(new PaySubscriptionCommand(1L, "tok"));

        assertThat(error(result).code()).isEqualTo("PLAN_NOT_FOUND");
        verifyNoInteractions(paymentGateway);
    }

    @Test
    void payCommandShouldRequireACardToken() {
        assertThatThrownBy(() -> new PaySubscriptionCommand(1L, " ")).hasMessage("payment.error.cardToken.notBlank");
        assertThatThrownBy(() -> new PaySubscriptionCommand(0L, "tok")).hasMessage("subscription.error.id.invalid");
    }
}
