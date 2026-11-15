package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditFindingRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AuditFindingPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditFindingPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AuditFindingPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the audit findings on Spring Data JPA.
 */
@Repository
public class AuditFindingRepositoryImpl implements IAuditFindingRepository {

    private final AuditFindingPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public AuditFindingRepositoryImpl(AuditFindingPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuditFinding save(AuditFinding auditFinding) {
        var entity = auditFinding.getId() == null
                ? new AuditFindingPersistenceEntity()
                : repository.findById(auditFinding.getId()).orElseGet(AuditFindingPersistenceEntity::new);
        return AuditFindingPersistenceAssembler.toDomain(repository.save(AuditFindingPersistenceAssembler.toEntity(auditFinding, entity)));
    }

    @Override
    public Optional<AuditFinding> findById(Long id) {
        return repository.findById(id).map(AuditFindingPersistenceAssembler::toDomain);
    }

    @Override
    public List<AuditFinding> findAll() {
        return repository.findAll().stream().map(AuditFindingPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<AuditFinding> findByAuditCode(String auditCode) {
        return repository.findByAuditCode(auditCode).stream().map(AuditFindingPersistenceAssembler::toDomain).toList();
    }
}
