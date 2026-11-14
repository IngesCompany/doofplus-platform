package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;

import java.util.List;
import java.util.Optional;

public interface ITaskCommentRepository {

    TaskComment save(TaskComment taskComment);

    Optional<TaskComment> findById(Long id);

    List<TaskComment> findAll();

    List<TaskComment> findByTaskId(Long taskId);
}