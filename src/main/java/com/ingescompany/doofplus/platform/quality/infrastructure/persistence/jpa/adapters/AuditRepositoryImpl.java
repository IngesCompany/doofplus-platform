package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AuditPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AuditPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditRepositoryImpl implements IAuditRepository {

    private final AuditPersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public AuditRepositoryImpl(
            AuditPersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Audit save(Audit audit) {
        AuditPersistenceEntity entity = AuditPersistenceAssembler.toEntity(audit);
        AuditPersistenceEntity savedEntity = repository.save(entity);
        Audit savedAudit = AuditPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(audit);

        return savedAudit;
    }

    @Override
    public Optional<Audit> findById(Long id) {
        return repository.findById(id)
                .map(AuditPersistenceAssembler::toDomain);
    }

    @Override
    public Optional<Audit> findByCode(String code) {
        return repository.findByCode(code)
                .map(AuditPersistenceAssembler::toDomain);
    }

    @Override
    public List<Audit> findAll() {
        return repository.findAll()
                .stream()
                .map(AuditPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}