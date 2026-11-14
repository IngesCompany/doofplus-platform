package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

/**
 * Command to activate a pending subscription once the payment is authorized (US52).
 *
 * @param subscriptionId subscription identifier
 */
public record ActivateSubscriptionCommand(Long subscriptionId) {
    public ActivateSubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscription.error.id.invalid");
        }
    }
}
