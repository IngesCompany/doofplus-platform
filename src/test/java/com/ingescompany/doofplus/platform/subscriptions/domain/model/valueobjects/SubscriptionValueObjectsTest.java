package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionValueObjectsTest {

    @Test
    void planKeyShouldAcceptLowercaseWordsSeparatedByHyphens() {
        assertThat(new PlanKey("standard-lab").value()).isEqualTo("standard-lab");
    }

    @Test
    void planKeyShouldRejectInvalidValues() {
        assertThatThrownBy(() -> new PlanKey(" ")).hasMessage("plan.error.planKey.notBlank");
        assertThatThrownBy(() -> new PlanKey("a".repeat(65))).hasMessage("plan.error.planKey.size");
        assertThatThrownBy(() -> new PlanKey("Standard Lab")).hasMessage("plan.error.planKey.pattern");
    }

    @Test
    void moneyShouldRejectNegativeAmountsAndInvalidCurrencies() {
        assertThat(new Money(BigDecimal.ZERO, "USD").currency()).isEqualTo("USD");
        assertThatThrownBy(() -> new Money(new BigDecimal("-1"), "USD")).hasMessage("money.error.amount.invalid");
        assertThatThrownBy(() -> new Money(BigDecimal.ONE, "usd")).hasMessage("money.error.currency.pattern");
    }

    @Test
    void paymentCardShouldRejectInvalidData() {
        assertThatThrownBy(() -> new PaymentCard("", "4242", "12/2030")).hasMessage("subscription.error.cardBrand.notBlank");
        assertThatThrownBy(() -> new PaymentCard("Visa", "42", "12/2030")).hasMessage("subscription.error.cardLast4.pattern");
        assertThatThrownBy(() -> new PaymentCard("Visa", "4242", "13/2030")).hasMessage("subscription.error.cardExpiry.pattern");
    }

    @Test
    void billingEmailShouldRejectInvalidAddresses() {
        assertThat(new BillingEmail("billing@andinos.com.pe").value()).isEqualTo("billing@andinos.com.pe");
        assertThatThrownBy(() -> new BillingEmail("")).hasMessage("subscription.error.billingEmail.notBlank");
        assertThatThrownBy(() -> new BillingEmail("billing-andinos")).hasMessage("subscription.error.billingEmail.pattern");
    }

    @Test
    void billingCycleShouldComputeTheEndOfThePeriod() {
        var start = java.time.LocalDate.of(2026, 1, 31);

        assertThat(BillingCycle.MONTHLY.nextPeriodEnd(start)).isEqualTo(java.time.LocalDate.of(2026, 2, 28));
        assertThat(BillingCycle.ANNUAL.nextPeriodEnd(start)).isEqualTo(java.time.LocalDate.of(2027, 1, 31));
    }
}
