package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

/**
 * Command to pay the next billing period of a subscription with a card token (US52).
 *
 * @param subscriptionId subscription identifier
 * @param cardToken      card token created by the payment gateway form in the Web App
 */
public record PaySubscriptionCommand(Long subscriptionId, String cardToken) {
    public PaySubscriptionCommand {
        if (subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("subscription.error.id.invalid");
        }
        if (cardToken == null || cardToken.isBlank()) {
            throw new IllegalArgumentException("payment.error.cardToken.notBlank");
        }
    }
}
