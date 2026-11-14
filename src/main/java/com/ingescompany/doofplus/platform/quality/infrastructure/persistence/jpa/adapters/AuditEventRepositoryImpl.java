package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditEventRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.AuditEventPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditEventPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.AuditEventPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditEventRepositoryImpl implements IAuditEventRepository {

    private final AuditEventPersistenceRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public AuditEventRepositoryImpl(
            AuditEventPersistenceRepository repository,
            DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public AuditEvent save(AuditEvent event) {
        AuditEventPersistenceEntity entity = AuditEventPersistenceAssembler.toEntity(event);
        AuditEventPersistenceEntity savedEntity = repository.save(entity);
        AuditEvent savedEvent = AuditEventPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(event);

        return savedEvent;
    }

    @Override
    public Optional<AuditEvent> findById(Long id) {
        return repository.findById(id)
                .map(AuditEventPersistenceAssembler::toDomain);
    }

    @Override
    public List<AuditEvent> findAll() {
        return repository.findAll()
                .stream()
                .map(AuditEventPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<AuditEvent> findByAggregateTypeAndAggregateId(
            String aggregateType,
            Long aggregateId) {
        return repository.findByAggregateTypeAndAggregateId(aggregateType, aggregateId)
                .stream()
                .map(AuditEventPersistenceAssembler::toDomain)
                .toList();
    }
}