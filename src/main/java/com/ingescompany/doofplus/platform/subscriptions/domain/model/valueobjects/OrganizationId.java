package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;

/**
 * Identifier of the organization that owns the subscription (Organizations & Profiles bounded context).
 *
 * @param value organization identifier
 */

public record OrganizationId(Long value) {
    private static final String INVALID_MESSAGE_KEY = "subscription.error.organizationId.invalid";

    public OrganizationId {
        if (value == null || value <= 0){
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
