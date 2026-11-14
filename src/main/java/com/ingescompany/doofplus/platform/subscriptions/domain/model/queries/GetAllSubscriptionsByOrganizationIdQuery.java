package com.ingescompany.doofplus.platform.subscriptions.domain.model.queries;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;

/**
 * Query for retrieving the subscriptions of an organization.
 *
 * @param organizationId organization identifier
 */
public record GetAllSubscriptionsByOrganizationIdQuery(OrganizationId organizationId) {
    public GetAllSubscriptionsByOrganizationIdQuery {
        if (organizationId == null) {
            throw new IllegalArgumentException("subscription.error.organizationId.invalid");
        }
    }
}
