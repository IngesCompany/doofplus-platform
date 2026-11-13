package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICollaborationTaskRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.CollaborationTaskPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CollaborationTaskPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.CollaborationTaskPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CollaborationTaskRepositoryImpl implements ICollaborationTaskRepository {

    private final CollaborationTaskPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public CollaborationTaskRepositoryImpl(
            CollaborationTaskPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public CollaborationTask save(CollaborationTask task) {
        CollaborationTaskPersistenceEntity entity =
                CollaborationTaskPersistenceAssembler.toEntity(task);

        CollaborationTaskPersistenceEntity savedEntity =
                persistenceRepository.save(entity);

        CollaborationTask savedTask =
                CollaborationTaskPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(task);

        return savedTask;
    }

    @Override
    public Optional<CollaborationTask> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(CollaborationTaskPersistenceAssembler::toDomain);
    }

    @Override
    public List<CollaborationTask> findAll() {
        return persistenceRepository.findAll()
                .stream()
                .map(CollaborationTaskPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<CollaborationTask> findByAssignedTo(Long assignedTo) {
        return persistenceRepository.findByAssignedTo(assignedTo)
                .stream()
                .map(CollaborationTaskPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<CollaborationTask> findByRelatedDeviationId(Long relatedDeviationId) {
        return persistenceRepository.findByRelatedDeviationId(relatedDeviationId)
                .stream()
                .map(CollaborationTaskPersistenceAssembler::toDomain)
                .toList();
    }
}