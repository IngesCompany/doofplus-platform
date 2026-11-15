package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITaskCommentRepository;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers.TaskCommentPersistenceAssembler;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TaskCommentPersistenceEntity;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories.TaskCommentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter of the domain repository of the task comments on Spring Data JPA.
 */
@Repository
public class TaskCommentRepositoryImpl implements ITaskCommentRepository {

    private final TaskCommentPersistenceRepository repository;

    /**
     * Creates the adapter.
     *
     * @param repository the Spring Data repository
     */
    public TaskCommentRepositoryImpl(TaskCommentPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskComment save(TaskComment taskComment) {
        var entity = taskComment.getId() == null
                ? new TaskCommentPersistenceEntity()
                : repository.findById(taskComment.getId()).orElseGet(TaskCommentPersistenceEntity::new);
        return TaskCommentPersistenceAssembler.toDomain(repository.save(TaskCommentPersistenceAssembler.toEntity(taskComment, entity)));
    }

    @Override
    public Optional<TaskComment> findById(Long id) {
        return repository.findById(id).map(TaskCommentPersistenceAssembler::toDomain);
    }

    @Override
    public List<TaskComment> findAll() {
        return repository.findAll().stream().map(TaskCommentPersistenceAssembler::toDomain).toList();
    }

    @Override
    public List<TaskComment> findByRecordCode(String recordCode) {
        return repository.findByRecordCode(recordCode).stream().map(TaskCommentPersistenceAssembler::toDomain).toList();
    }
}
