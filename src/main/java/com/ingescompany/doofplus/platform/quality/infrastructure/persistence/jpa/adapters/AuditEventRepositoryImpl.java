package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditEventRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AuditEventPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditEventPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AuditEventPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the audit events on Spring Data JPA.
 */
@Repository
public class AuditEventRepositoryImpl implements IAuditEventRepository {

    private final AuditEventPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public AuditEventRepositoryImpl(AuditEventPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuditEvent save(AuditEvent auditEvent) {
        var entity = auditEvent.getId() == null
                ? new AuditEventPersistenceEntity()
                : repository.findById(auditEvent.getId()).orElseGet(AuditEventPersistenceEntity::new);
        return AuditEventPersistenceAssembler.toDomain(repository.save(AuditEventPersistenceAssembler.toEntity(auditEvent, entity)));
    }

    @Override
    public Optional<AuditEvent> findById(Long id) {
        return repository.findById(id).map(AuditEventPersistenceAssembler::toDomain);
    }

    @Override
    public List<AuditEvent> findAll() {
        return repository.findAll().stream().map(AuditEventPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<AuditEvent> findByRecord(String record) {
        return repository.findByRecord(record).stream().map(AuditEventPersistenceAssembler::toDomain).toList();
    }
}
