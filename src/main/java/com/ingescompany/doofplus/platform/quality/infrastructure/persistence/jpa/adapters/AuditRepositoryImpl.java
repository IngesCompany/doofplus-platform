package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AuditPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AuditPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the audits on Spring Data JPA.
 */
@Repository
public class AuditRepositoryImpl implements IAuditRepository {

    private final AuditPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public AuditRepositoryImpl(AuditPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Audit save(Audit audit) {
        var entity = audit.getId() == null
                ? new AuditPersistenceEntity()
                : repository.findById(audit.getId()).orElseGet(AuditPersistenceEntity::new);
        return AuditPersistenceAssembler.toDomain(repository.save(AuditPersistenceAssembler.toEntity(audit, entity)));
    }

    @Override
    public Optional<Audit> findById(Long id) {
        return repository.findById(id).map(AuditPersistenceAssembler::toDomain);
    }

    @Override
    public List<Audit> findAll() {
        return repository.findAll().stream().map(AuditPersistenceAssembler::toDomain).toList();
    }

    @Override
    public boolean existsByCode(String code, Long id) {
        return id == null ? repository.existsByCode(code) : repository.existsByCodeAndIdNot(code, id);
    }
}
