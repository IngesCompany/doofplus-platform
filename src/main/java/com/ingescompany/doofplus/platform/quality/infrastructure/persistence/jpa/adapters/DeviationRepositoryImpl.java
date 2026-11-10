package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.DeviationPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.DeviationPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DeviationRepositoryImpl implements IDeviationRepository {

    private final DeviationPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public DeviationRepositoryImpl(
            DeviationPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Deviation save(Deviation deviation) {
        var saved = persistenceRepository.save(
                DeviationPersistenceAssembler.toPersistenceFromDomain(deviation)
        );

        domainEventPublisher.publishAndClearEvents(deviation);

        return DeviationPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<Deviation> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(DeviationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Deviation> findByCode(String code) {
        return persistenceRepository.findByCode(code)
                .map(DeviationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Deviation> findAll() {
        return persistenceRepository.findAll().stream()
                .map(DeviationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Deviation> findByBatchId(Long batchId) {
        return persistenceRepository.findByBatchId(batchId).stream()
                .map(DeviationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return persistenceRepository.existsByCode(code);
    }
}