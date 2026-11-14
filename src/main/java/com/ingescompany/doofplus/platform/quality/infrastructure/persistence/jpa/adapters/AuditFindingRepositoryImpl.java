package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditFindingRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AuditFindingPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditFindingPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AuditFindingPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditFindingRepositoryImpl implements IAuditFindingRepository {

    private final AuditFindingPersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public AuditFindingRepositoryImpl(
            AuditFindingPersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public AuditFinding save(AuditFinding finding) {
        AuditFindingPersistenceEntity entity =
                AuditFindingPersistenceAssembler.toEntity(finding);

        AuditFindingPersistenceEntity savedEntity = repository.save(entity);
        AuditFinding savedFinding =
                AuditFindingPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(finding);

        return savedFinding;
    }

    @Override
    public Optional<AuditFinding> findById(Long id) {
        return repository.findById(id)
                .map(AuditFindingPersistenceAssembler::toDomain);
    }

    @Override
    public List<AuditFinding> findAll() {
        return repository.findAll()
                .stream()
                .map(AuditFindingPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<AuditFinding> findByAuditId(Long auditId) {
        return repository.findByAuditId(auditId)
                .stream()
                .map(AuditFindingPersistenceAssembler::toDomain)
                .toList();
    }
}