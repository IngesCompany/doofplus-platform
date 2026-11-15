package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.CapaPlanPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaPlanPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.CapaPlanPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the capa plans on Spring Data JPA.
 */
@Repository
public class CapaPlanRepositoryImpl implements ICapaPlanRepository {

    private final CapaPlanPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public CapaPlanRepositoryImpl(CapaPlanPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public CapaPlan save(CapaPlan capaPlan) {
        var entity = capaPlan.getId() == null
                ? new CapaPlanPersistenceEntity()
                : repository.findById(capaPlan.getId()).orElseGet(CapaPlanPersistenceEntity::new);
        return CapaPlanPersistenceAssembler.toDomain(repository.save(CapaPlanPersistenceAssembler.toEntity(capaPlan, entity)));
    }

    @Override
    public Optional<CapaPlan> findById(Long id) {
        return repository.findById(id).map(CapaPlanPersistenceAssembler::toDomain);
    }

    @Override
    public List<CapaPlan> findAll() {
        return repository.findAll().stream().map(CapaPlanPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<CapaPlan> findBySourceDeviation(String sourceDeviation) {
        return repository.findBySourceDeviation(sourceDeviation).stream().map(CapaPlanPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code, Long id) {
        return id == null ? repository.existsByCode(code) : repository.existsByCodeAndIdNot(code, id);
    }
}
