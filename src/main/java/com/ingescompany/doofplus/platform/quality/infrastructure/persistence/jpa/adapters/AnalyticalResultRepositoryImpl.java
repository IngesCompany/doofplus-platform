package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAnalyticalResultRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AnalyticalResultPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AnalyticalResultPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AnalyticalResultPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AnalyticalResultRepositoryImpl implements IAnalyticalResultRepository {

    private final AnalyticalResultPersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public AnalyticalResultRepositoryImpl(
            AnalyticalResultPersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public AnalyticalResult save(AnalyticalResult result) {
        AnalyticalResultPersistenceEntity entity =
                AnalyticalResultPersistenceAssembler.toEntity(result);

        AnalyticalResultPersistenceEntity savedEntity = repository.save(entity);
        AnalyticalResult savedResult =
                AnalyticalResultPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(result);

        return savedResult;
    }

    @Override
    public Optional<AnalyticalResult> findById(Long id) {
        return repository.findById(id)
                .map(AnalyticalResultPersistenceAssembler::toDomain);
    }

    @Override
    public List<AnalyticalResult> findAll() {
        return repository.findAll()
                .stream()
                .map(AnalyticalResultPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<AnalyticalResult> findByBatchId(Long batchId) {
        return repository.findByBatchId(batchId)
                .stream()
                .map(AnalyticalResultPersistenceAssembler::toDomain)
                .toList();
    }
}