package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the collaboration tasks.
 */
public interface ICollaborationTaskRepository {

    /**
     * Saves a new or changed collaboration task.
     *
     * @param collaborationTask the collaboration task
     * @return the saved collaboration task, with its identifier
     */
    CollaborationTask save(CollaborationTask collaborationTask);

    /**
     * Finds a collaboration task by its identifier.
     *
     * @param id the identifier
     * @return the collaboration task, if it exists
     */
    Optional<CollaborationTask> findById(Long id);

    /**
     * Lists all the collaboration tasks.
     *
     * @return the collaboration tasks
     */
    List<CollaborationTask> findAll();

    /**
     * Lists the collaboration tasks with the given record code.
     *
     * @param recordCode the record code
     * @return the collaboration tasks
     */
    List<CollaborationTask> findByRecordCode(String recordCode);
}
