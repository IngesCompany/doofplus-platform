package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.ingescompany.doofplus.platform.subscriptions.domain.repositories.SubscriptionRepository;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.assemblers.SubscriptionPersistenceAssembler;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.repositories.SubscriptionPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link SubscriptionRepository}.
 */
@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionPersistenceRepository subscriptionPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public SubscriptionRepositoryImpl(SubscriptionPersistenceRepository subscriptionPersistenceRepository,
                                      DomainEventPublisher domainEventPublisher) {
        this.subscriptionPersistenceRepository = subscriptionPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Subscription save(Subscription subscription) {
        var savedEntity = subscriptionPersistenceRepository.saveAndFlush(
                SubscriptionPersistenceAssembler.toPersistenceFromDomain(subscription));
        domainEventPublisher.publishAndClearEvents(subscription);
        return SubscriptionPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionPersistenceRepository.findById(id).map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Subscription> findAllByOrganizationId(OrganizationId organizationId) {
        return subscriptionPersistenceRepository.findAllByOrganizationId(organizationId).stream()
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByOrganizationIdAndStatusIn(OrganizationId organizationId, Collection<SubscriptionStatus> statuses) {
        return subscriptionPersistenceRepository.existsByOrganizationIdAndStatusIn(organizationId, statuses);
    }
}
