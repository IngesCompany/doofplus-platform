package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.TraceabilityGapPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TraceabilityGapPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.TraceabilityGapPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the traceability gaps on Spring Data JPA.
 */
@Repository
public class TraceabilityGapRepositoryImpl implements ITraceabilityGapRepository {

    private final TraceabilityGapPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public TraceabilityGapRepositoryImpl(TraceabilityGapPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public TraceabilityGap save(TraceabilityGap traceabilityGap) {
        var entity = traceabilityGap.getId() == null
                ? new TraceabilityGapPersistenceEntity()
                : repository.findById(traceabilityGap.getId()).orElseGet(TraceabilityGapPersistenceEntity::new);
        return TraceabilityGapPersistenceAssembler.toDomain(repository.save(TraceabilityGapPersistenceAssembler.toEntity(traceabilityGap, entity)));
    }

    @Override
    public Optional<TraceabilityGap> findById(Long id) {
        return repository.findById(id).map(TraceabilityGapPersistenceAssembler::toDomain);
    }

    @Override
    public List<TraceabilityGap> findAll() {
        return repository.findAll().stream().map(TraceabilityGapPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<TraceabilityGap> findByBatchCode(String batchCode) {
        return repository.findByBatchCode(batchCode).stream().map(TraceabilityGapPersistenceAssembler::toDomain).toList();
    }
}
