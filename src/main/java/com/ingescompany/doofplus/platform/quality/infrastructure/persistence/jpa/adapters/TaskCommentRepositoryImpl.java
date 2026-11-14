package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITaskCommentRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.TaskCommentPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TaskCommentPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.TaskCommentPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskCommentRepositoryImpl implements ITaskCommentRepository {

    private final TaskCommentPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public TaskCommentRepositoryImpl(
            TaskCommentPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public TaskComment save(TaskComment comment) {
        TaskCommentPersistenceEntity entity =
                TaskCommentPersistenceAssembler.toEntity(comment);

        TaskCommentPersistenceEntity savedEntity =
                persistenceRepository.save(entity);

        TaskComment savedComment =
                TaskCommentPersistenceAssembler.toDomain(savedEntity);

        domainEventPublisher.publishAndClearEvents(comment);

        return savedComment;
    }

    @Override
    public Optional<TaskComment> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(TaskCommentPersistenceAssembler::toDomain);
    }

    @Override
    public List<TaskComment> findAll() {
        return persistenceRepository.findAll()
                .stream()
                .map(TaskCommentPersistenceAssembler::toDomain)
                .toList();
    }

    @Override
    public List<TaskComment> findByTaskId(Long taskId) {
        return persistenceRepository.findByTaskId(taskId)
                .stream()
                .map(TaskCommentPersistenceAssembler::toDomain)
                .toList();
    }
}