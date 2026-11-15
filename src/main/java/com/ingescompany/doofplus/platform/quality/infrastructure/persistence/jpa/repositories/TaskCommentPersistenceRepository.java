package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TaskCommentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the task_comments table.
 */
@Repository
public interface TaskCommentPersistenceRepository extends JpaRepository<TaskCommentPersistenceEntity, Long> {

    /**
     * Lists the rows with the given record code.
     *
     * @param recordCode the record code
     * @return the rows
     */
    List<TaskCommentPersistenceEntity> findByRecordCode(String recordCode);
}
