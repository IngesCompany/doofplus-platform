package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.DeviationPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.DeviationPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.DeviationPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the deviations on Spring Data JPA.
 */
@Repository
public class DeviationRepositoryImpl implements IDeviationRepository {

    private final DeviationPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public DeviationRepositoryImpl(DeviationPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Deviation save(Deviation deviation) {
        var entity = deviation.getId() == null
                ? new DeviationPersistenceEntity()
                : repository.findById(deviation.getId()).orElseGet(DeviationPersistenceEntity::new);
        return DeviationPersistenceAssembler.toDomain(repository.save(DeviationPersistenceAssembler.toEntity(deviation, entity)));
    }

    @Override
    public Optional<Deviation> findById(Long id) {
        return repository.findById(id).map(DeviationPersistenceAssembler::toDomain);
    }

    @Override
    public List<Deviation> findAll() {
        return repository.findAll().stream().map(DeviationPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<Deviation> findByBatchCode(String batchCode) {
        return repository.findByBatchCode(batchCode).stream().map(DeviationPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code, Long id) {
        return id == null ? repository.existsByCode(code) : repository.existsByCodeAndIdNot(code, id);
    }
}
