package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.TraceabilityGapPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TraceabilityGapPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.TraceabilityGapPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TraceabilityGapRepositoryImpl implements ITraceabilityGapRepository {

    private final TraceabilityGapPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public TraceabilityGapRepositoryImpl(
            TraceabilityGapPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public TraceabilityGap save(TraceabilityGap gap) {
        TraceabilityGapPersistenceEntity entity =
                TraceabilityGapPersistenceAssembler.toEntity(gap);

        TraceabilityGapPersistenceEntity savedEntity =
                persistenceRepository.save(entity);

        TraceabilityGap savedGap =
                TraceabilityGapPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(gap);

        return savedGap;
    }

    @Override
    public Optional<TraceabilityGap> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(TraceabilityGapPersistenceAssembler::toDomain);
    }

    @Override
    public List<TraceabilityGap> findAll() {
        return persistenceRepository.findAll()
                .stream()
                .map(TraceabilityGapPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<TraceabilityGap> findByBatchId(Long batchId) {
        return persistenceRepository.findByBatchId(batchId)
                .stream()
                .map(TraceabilityGapPersistenceAssembler::toDomain)
                .toList();
    }
}