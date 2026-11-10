package com.ingescompany.doofplus.platform.subscriptions.application.internal.queryservices;

import com.ingescompany.doofplus.platform.subscriptions.application.queryservices.SubscriptionQueryService;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetAllSubscriptionsByOrganizationIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetSubscriptionByIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.repositories.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service for subscription query operations.
 *
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        log.debug("Querying subscription by id={}", query.id());
        return subscriptionRepository.findById(query.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subscription> handle(GetAllSubscriptionsByOrganizationIdQuery query) {
        var results = subscriptionRepository.findAllByOrganizationId(query.organizationId());
        log.debug("Found {} subscription(s) for organizationId={}", results.size(), query.organizationId().value());
        return results;
    }
}
