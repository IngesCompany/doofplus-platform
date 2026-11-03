package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskCommentJpaRepository extends JpaRepository<TaskComment, UUID> {
    List<TaskComment> findByTaskId(UUID taskId);
}