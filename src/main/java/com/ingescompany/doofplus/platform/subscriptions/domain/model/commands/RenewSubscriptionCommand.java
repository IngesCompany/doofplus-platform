package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

/**
 * Command to renew an active subscription for one more billing period (US53).
 *
 * @param subscriptionId subscription identifier
 */
public record RenewSubscriptionCommand(Long subscriptionId) {
    public RenewSubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscription.error.id.invalid");
        }
    }
}
