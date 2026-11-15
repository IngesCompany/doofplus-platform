package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the task comments.
 */
public interface ITaskCommentRepository {

    /**
     * Saves a new or changed task comment.
     *
     * @param taskComment the task comment
     * @return the saved task comment, with its identifier
     */
    TaskComment save(TaskComment taskComment);

    /**
     * Finds a task comment by its identifier.
     *
     * @param id the identifier
     * @return the task comment, if it exists
     */
    Optional<TaskComment> findById(Long id);

    /**
     * Lists all the task comments.
     *
     * @return the task comments
     */
    List<TaskComment> findAll();

    /**
     * Lists the task comments with the given record code.
     *
     * @param recordCode the record code
     * @return the task comments
     */
    List<TaskComment> findByRecordCode(String recordCode);
}
