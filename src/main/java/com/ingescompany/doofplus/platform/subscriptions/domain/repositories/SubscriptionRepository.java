package com.ingescompany.doofplus.platform.subscriptions.domain.repositories;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository contract for subscriptions, implemented in the infrastructure layer.
 */
public interface SubscriptionRepository {
    Subscription save(Subscription subscription);
    Optional<Subscription> findById(Long id);
    List<Subscription> findAllByOrganizationId(OrganizationId organizationId);
    boolean existsByOrganizationIdAndStatusIn(OrganizationId organizationId, Collection<SubscriptionStatus> statuses);
}
