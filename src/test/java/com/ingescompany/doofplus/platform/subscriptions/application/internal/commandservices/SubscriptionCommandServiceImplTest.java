package com.ingescompany.doofplus.platform.subscriptions.application.internal.commandservices;

import com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway.PaymentGateway;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.ActivateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionCommandServiceImplTest {

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

    private static CreateSubscriptionCommand createCommand() {
        return new CreateSubscriptionCommand(new OrganizationId(1L), 1L, BillingCycle.MONTHLY,
                new PaymentCard("Visa", "4242", "12/2030"), new BillingEmail("billing@andinos.com.pe"));
    }

    private static ApplicationError error(Result<Subscription, ApplicationError> result) {
        return ((Result.Failure<Subscription, ApplicationError>) result).error();
    }

    @Test
    void createShouldFailWhenThePlanDoesNotExist() {
        when(planRepository.existsById(1L)).thenReturn(false);

        var result = service.handle(createCommand());

        assertThat(error(result).code()).isEqualTo("PLAN_NOT_FOUND");
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void createShouldFailWhenTheOrganizationIsAlreadySubscribed() {
        when(planRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.existsByOrganizationIdAndStatusIn(any(), anyCollection())).thenReturn(true);

        var result = service.handle(createCommand());

        assertThat(error(result).code()).isEqualTo("SUBSCRIPTION_CONFLICT");
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void createShouldSaveAPendingSubscription() {
        when(planRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.existsByOrganizationIdAndStatusIn(any(), anyCollection())).thenReturn(false);
        when(subscriptionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.handle(createCommand());

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.toOptional().orElseThrow().getStatus()).isEqualTo(SubscriptionStatus.PENDING);
    }

    @Test
    void activateShouldFailWhenTheSubscriptionDoesNotExist() {
        when(subscriptionRepository.findById(9L)).thenReturn(Optional.empty());

        var result = service.handle(new ActivateSubscriptionCommand(9L));

        assertThat(error(result).code()).isEqualTo("SUBSCRIPTION_NOT_FOUND");
    }

    @Test
    void activateShouldSaveTheActiveSubscription() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(new Subscription(createCommand())));
        when(subscriptionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.handle(new ActivateSubscriptionCommand(1L));

        assertThat(result.toOptional().orElseThrow().getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
    }

    @Test
    void cancelShouldReportABusinessRuleViolationWhenTheSubscriptionIsPending() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(new Subscription(createCommand())));

        var result = service.handle(new CancelSubscriptionCommand(1L));

        assertThat(error(result).code()).isEqualTo("BUSINESS_RULE_VIOLATION");
        verify(subscriptionRepository, never()).save(any());
    }
}
