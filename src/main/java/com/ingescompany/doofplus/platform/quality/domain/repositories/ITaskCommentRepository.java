package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITaskCommentRepository {
    TaskComment save(TaskComment comment);
    Optional<TaskComment> findById(UUID id);
    List<TaskComment> findAll();
    List<TaskComment> findByTaskId(UUID taskId);
    void deleteById(UUID id);
}