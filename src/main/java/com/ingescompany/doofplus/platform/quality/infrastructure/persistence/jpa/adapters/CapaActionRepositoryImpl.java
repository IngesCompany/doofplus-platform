package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaActionRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.CapaActionPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaActionPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.CapaActionPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the capa actions on Spring Data JPA.
 */
@Repository
public class CapaActionRepositoryImpl implements ICapaActionRepository {

    private final CapaActionPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public CapaActionRepositoryImpl(CapaActionPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public CapaAction save(CapaAction capaAction) {
        var entity = capaAction.getId() == null
                ? new CapaActionPersistenceEntity()
                : repository.findById(capaAction.getId()).orElseGet(CapaActionPersistenceEntity::new);
        return CapaActionPersistenceAssembler.toDomain(repository.save(CapaActionPersistenceAssembler.toEntity(capaAction, entity)));
    }

    @Override
    public Optional<CapaAction> findById(Long id) {
        return repository.findById(id).map(CapaActionPersistenceAssembler::toDomain);
    }

    @Override
    public List<CapaAction> findAll() {
        return repository.findAll().stream().map(CapaActionPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<CapaAction> findByCapaCode(String capaCode) {
        return repository.findByCapaCode(capaCode).stream().map(CapaActionPersistenceAssembler::toDomain).toList();
    }
}
