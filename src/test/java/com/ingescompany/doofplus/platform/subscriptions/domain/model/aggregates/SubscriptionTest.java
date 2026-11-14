package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionTest {

    static Subscription pendingSubscription(BillingCycle billingCycle) {
        return new Subscription(new CreateSubscriptionCommand(new OrganizationId(1L), 1L, billingCycle,
                new PaymentCard("Visa", "4242", "12/2030"), new BillingEmail("billing@andinos.com.pe")));
    }

    @Test
    void newSubscriptionShouldBePendingWithAutoRenew() {
        var subscription = pendingSubscription(BillingCycle.MONTHLY);

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.PENDING);
        assertThat(subscription.isAutoRenew()).isTrue();
        assertThat(subscription.isCurrent()).isTrue();
        assertThat(subscription.getCurrentPeriodStart()).isNull();
    }

    @Test
    void activateShouldStartAMonthlyPeriod() {
        var subscription = pendingSubscription(BillingCycle.MONTHLY);

        subscription.activate();

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
        assertThat(subscription.getCurrentPeriodStart()).isEqualTo(LocalDate.now());
        assertThat(subscription.getCurrentPeriodEnd()).isEqualTo(LocalDate.now().plusMonths(1));
    }

    @Test
    void activateShouldStartAnAnnualPeriod() {
        var subscription = pendingSubscription(BillingCycle.ANNUAL);

        subscription.activate();

        assertThat(subscription.getCurrentPeriodEnd()).isEqualTo(LocalDate.now().plusYears(1));
    }

    @Test
    void activateShouldFailWhenTheSubscriptionIsNotPending() {
        var subscription = pendingSubscription(BillingCycle.MONTHLY);
        subscription.activate();

        assertThatThrownBy(subscription::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void renewShouldExtendTheSubscriptionOnePeriod() {
        var subscription = pendingSubscription(BillingCycle.MONTHLY);
        subscription.activate();
        var previousEnd = subscription.getCurrentPeriodEnd();

        subscription.renew();

        assertThat(subscription.getCurrentPeriodStart()).isEqualTo(previousEnd);
        assertThat(subscription.getCurrentPeriodEnd()).isEqualTo(previousEnd.plusMonths(1));
    }

    @Test
    void renewShouldFailWhenTheSubscriptionIsNotActive() {
        assertThatThrownBy(pendingSubscription(BillingCycle.MONTHLY)::renew).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cancelShouldKeepThePaidPeriodAndStopTheAutoRenew() {
        var subscription = pendingSubscription(BillingCycle.MONTHLY);
        subscription.activate();
        var periodEnd = subscription.getCurrentPeriodEnd();

        subscription.cancel();

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.CANCELLED);
        assertThat(subscription.isAutoRenew()).isFalse();
        assertThat(subscription.isCurrent()).isFalse();
        assertThat(subscription.getCurrentPeriodEnd()).isEqualTo(periodEnd);
    }

    @Test
    void cancelShouldFailWhenTheSubscriptionIsNotActive() {
        assertThatThrownBy(pendingSubscription(BillingCycle.MONTHLY)::cancel).isInstanceOf(IllegalStateException.class);
    }
}
