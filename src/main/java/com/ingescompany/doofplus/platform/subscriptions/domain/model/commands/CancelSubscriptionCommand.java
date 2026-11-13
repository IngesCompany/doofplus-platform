package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

/**
 * Command to cancel an active subscription (US53).
 *
 * @param subscriptionId subscription identifier
 */
public record CancelSubscriptionCommand(Long subscriptionId) {
    public CancelSubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscription.error.id.invalid");
        }
    }
}
