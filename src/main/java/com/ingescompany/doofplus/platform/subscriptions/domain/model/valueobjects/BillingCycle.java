package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;

import java.time.LocalDate;

/**
 * Billing frequency of a subscription.
 */
public enum BillingCycle {
    MONTHLY,
    ANNUAL;

    /**
     * Computes the end of the billing period that starts on the given date.
     *
     * @param from start of the period
     * @return end of the period
     */
    public LocalDate nextPeriodEnd(LocalDate from) {
        return this == MONTHLY ? from.plusMonths(1) : from.plusYears(1);
    }
}
