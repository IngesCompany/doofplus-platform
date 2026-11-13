package com.ingescompany.doofplus.platform.subscriptions.application.queryservices;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetAllSubscriptionsByOrganizationIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to subscriptions.
 *
 * @since 1.0
 */
public interface SubscriptionQueryService {
    /**
     * Retrieves a subscription by its identifier.
     *
     * @param query query containing the subscription identifier
     * @return subscription when found, otherwise empty
     */
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);

    /**
     * Retrieves the subscriptions of an organization.
     *
     * @param query query containing the organization identifier
     * @return list of subscriptions, possibly empty
     */
    List<Subscription> handle(GetAllSubscriptionsByOrganizationIdQuery query);
}
