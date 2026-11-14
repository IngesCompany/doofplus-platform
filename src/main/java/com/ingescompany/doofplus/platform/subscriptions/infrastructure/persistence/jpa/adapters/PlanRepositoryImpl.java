package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import com.ingescompany.doofplus.platform.subscriptions.domain.repositories.PlanRepository;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.assemblers.PlanPersistenceAssembler;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.repositories.PlanPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link PlanRepository}.
 */
@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanPersistenceRepository planPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public PlanRepositoryImpl(PlanPersistenceRepository planPersistenceRepository, DomainEventPublisher domainEventPublisher) {
        this.planPersistenceRepository = planPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Plan save(Plan plan) {
        var savedEntity = planPersistenceRepository.saveAndFlush(PlanPersistenceAssembler.toPersistenceFromDomain(plan));
        domainEventPublisher.publishAndClearEvents(plan);
        return PlanPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return planPersistenceRepository.findById(id).map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Plan> findAll() {
        return planPersistenceRepository.findAll().stream().map(PlanPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsById(Long id) {
        return planPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByPlanKey(PlanKey planKey) {
        return planPersistenceRepository.existsByPlanKey(planKey);
    }
}
