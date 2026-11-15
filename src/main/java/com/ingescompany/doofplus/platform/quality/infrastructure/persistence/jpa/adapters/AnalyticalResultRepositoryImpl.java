package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAnalyticalResultRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AnalyticalResultPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AnalyticalResultPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AnalyticalResultPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the analytical results on Spring Data JPA.
 */
@Repository
public class AnalyticalResultRepositoryImpl implements IAnalyticalResultRepository {

    private final AnalyticalResultPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public AnalyticalResultRepositoryImpl(AnalyticalResultPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public AnalyticalResult save(AnalyticalResult analyticalResult) {
        var entity = analyticalResult.getId() == null
                ? new AnalyticalResultPersistenceEntity()
                : repository.findById(analyticalResult.getId()).orElseGet(AnalyticalResultPersistenceEntity::new);
        return AnalyticalResultPersistenceAssembler.toDomain(repository.save(AnalyticalResultPersistenceAssembler.toEntity(analyticalResult, entity)));
    }

    @Override
    public Optional<AnalyticalResult> findById(Long id) {
        return repository.findById(id).map(AnalyticalResultPersistenceAssembler::toDomain);
    }

    @Override
    public List<AnalyticalResult> findAll() {
        return repository.findAll().stream().map(AnalyticalResultPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<AnalyticalResult> findByBatchCode(String batchCode) {
        return repository.findByBatchCode(batchCode).stream().map(AnalyticalResultPersistenceAssembler::toDomain).toList();
    }
}
