package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.CapaPlanPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.CapaPlanPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CapaPlanRepositoryImpl implements ICapaPlanRepository {

    private final CapaPlanPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public CapaPlanRepositoryImpl(
            CapaPlanPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public CapaPlan save(CapaPlan capaPlan) {
        var saved = persistenceRepository.save(
                CapaPlanPersistenceAssembler.toEntity(capaPlan)
        );

        domainEventPublisher.publishAndClearEvents(capaPlan);

        return CapaPlanPersistenceAssembler.toDomain(saved);
    }

    @Override
    public Optional<CapaPlan> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(CapaPlanPersistenceAssembler::toDomain);
    }

    @Override
    public List<CapaPlan> findAll() {
        return persistenceRepository.findAll().stream()
                .map(CapaPlanPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<CapaPlan> findByDeviationId(Long deviationId) {
        return persistenceRepository.findByDeviationId(deviationId).stream()
                .map(CapaPlanPersistenceAssembler::toDomain)
                .toList();
    }
}