package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.entities.Payment;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionPaymentsTest {

    private static Subscription storedSubscription(SubscriptionStatus status, List<Payment> payments) {
        return Subscription.reconstitute(12L, new OrganizationId(1L), 1L, BillingCycle.MONTHLY, status, true,
                new PaymentCard("Visa", "4242", "12/2030"), new BillingEmail("billing@andinos.com.pe"),
                null, null, payments);
    }

    private static Payment payment(String invoiceNumber) {
        return new Payment(invoiceNumber, new Money(new BigDecimal("49.90"), "USD"));
    }

    @Test
    void recordPaymentShouldAddThePaymentToTheHistory() {
        var subscription = storedSubscription(SubscriptionStatus.PENDING, List.of());

        subscription.recordPayment(payment("INV-12-1"));

        assertThat(subscription.getPayments()).extracting(Payment::getInvoiceNumber).containsExactly("INV-12-1");
    }

    @Test
    void recordPaymentShouldRejectANullPayment() {
        var subscription = storedSubscription(SubscriptionStatus.PENDING, List.of());

        assertThatThrownBy(() -> subscription.recordPayment(null)).hasMessage("payment.error.notNull");
    }

    @Test
    void paymentsShouldNotBeModifiableFromOutside() {
        var subscription = storedSubscription(SubscriptionStatus.PENDING, List.of());

        assertThatThrownBy(() -> subscription.getPayments().add(payment("INV-12-1")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void nextInvoiceNumberShouldCountTheStoredPayments() {
        var subscription = storedSubscription(SubscriptionStatus.ACTIVE, List.of(payment("INV-12-1"), payment("INV-12-2")));

        assertThat(subscription.nextInvoiceNumber()).isEqualTo("INV-12-3");
    }

    @Test
    void applyApprovedPaymentShouldActivateAPendingSubscription() {
        var subscription = storedSubscription(SubscriptionStatus.PENDING, List.of());

        subscription.applyApprovedPayment();

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
        assertThat(subscription.getCurrentPeriodEnd()).isNotNull();
    }

    @Test
    void applyApprovedPaymentShouldRenewAnActiveSubscription() {
        var subscription = storedSubscription(SubscriptionStatus.PENDING, List.of());
        subscription.applyApprovedPayment();
        var previousEnd = subscription.getCurrentPeriodEnd();

        subscription.applyApprovedPayment();

        assertThat(subscription.getCurrentPeriodStart()).isEqualTo(previousEnd);
        assertThat(subscription.getCurrentPeriodEnd()).isEqualTo(previousEnd.plusMonths(1));
    }
}
