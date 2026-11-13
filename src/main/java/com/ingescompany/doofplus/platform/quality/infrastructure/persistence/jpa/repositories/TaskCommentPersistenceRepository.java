package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TaskCommentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCommentPersistenceRepository
        extends JpaRepository<TaskCommentPersistenceEntity, Long> {

    List<TaskCommentPersistenceEntity> findByTaskId(Long taskId);
}