package com.ingescompany.doofplus.platform.subscriptions.domain.model.queries;

/**
 * Query for retrieving a subscription by its identifier.
 *
 * @param id subscription identifier
 */
public record GetSubscriptionByIdQuery(Long id) {
    public GetSubscriptionByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("subscription.error.id.invalid");
        }
    }
}
