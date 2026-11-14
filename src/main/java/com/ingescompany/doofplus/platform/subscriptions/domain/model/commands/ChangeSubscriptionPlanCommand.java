package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;

/**
 * Command to change the plan and the billing cycle of a pending or active subscription (US53).
 *
 * @param subscriptionId subscription identifier
 * @param planId         identifier of the new plan
 * @param billingCycle   new billing cycle
 */
public record ChangeSubscriptionPlanCommand(Long subscriptionId, Long planId, BillingCycle billingCycle) {
    public ChangeSubscriptionPlanCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscription.error.id.invalid");
        }
        if (planId == null || planId <= 0) {
            throw new IllegalArgumentException("plan.error.id.invalid");
        }
        if (billingCycle == null) {
            throw new IllegalArgumentException("subscription.error.billingCycle.invalid");
        }
    }
}
