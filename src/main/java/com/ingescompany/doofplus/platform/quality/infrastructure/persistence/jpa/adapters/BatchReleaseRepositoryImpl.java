package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IBatchReleaseRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.BatchReleasePersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.BatchReleasePersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BatchReleaseRepositoryImpl implements IBatchReleaseRepository {

    private final BatchReleasePersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public BatchReleaseRepositoryImpl(
            BatchReleasePersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public BatchRelease save(BatchRelease batchRelease) {
        var saved = persistenceRepository.save(
                BatchReleasePersistenceAssembler.toEntity(batchRelease)
        );

        domainEventPublisher.publishAndClearEvents(batchRelease);

        return BatchReleasePersistenceAssembler.toDomain(saved);
    }

    @Override
    public Optional<BatchRelease> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(BatchReleasePersistenceAssembler::toDomain);
    }

    @Override
    public Optional<BatchRelease> findByBatchId(Long batchId) {
        return persistenceRepository.findByBatchId(batchId)
                .map(BatchReleasePersistenceAssembler::toDomain);
    }

    @Override
    public List<BatchRelease> findAll() {
        return persistenceRepository.findAll().stream()
                .map(BatchReleasePersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public boolean existsByBatchId(Long batchId) {
        return persistenceRepository.existsByBatchId(batchId);
    }
}