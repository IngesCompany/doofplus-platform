package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaActionRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.CapaActionPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.CapaActionPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CapaActionRepositoryImpl implements ICapaActionRepository {

    private final CapaActionPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public CapaActionRepositoryImpl(
            CapaActionPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public CapaAction save(CapaAction capaAction) {
        var saved = persistenceRepository.save(
                CapaActionPersistenceAssembler.toEntity(capaAction)
        );

        domainEventPublisher.publishAndClearEvents(capaAction);

        return CapaActionPersistenceAssembler.toDomain(saved);
    }

    @Override
    public Optional<CapaAction> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(CapaActionPersistenceAssembler::toDomain);
    }

    @Override
    public List<CapaAction> findAll() {
        return persistenceRepository.findAll().stream()
                .map(CapaActionPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<CapaAction> findByCapaPlanId(Long capaPlanId) {
        return persistenceRepository.findByCapaPlanId(capaPlanId).stream()
                .map(CapaActionPersistenceAssembler::toDomain)
                .toList();
    }
}