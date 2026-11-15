package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCollaborationTaskByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the collaboration tasks.
 */
public interface CollaborationTaskQueryService {

    /**
     * Lists the collaboration tasks, filtered when the query has a value.
     *
     * @param query the query
     * @return the collaboration tasks
     */
    List<CollaborationTask> handle(GetAllCollaborationTasksQuery query);

    /**
     * Finds a collaboration task by its identifier.
     *
     * @param query the query
     * @return the collaboration task, if it exists
     */
    Optional<CollaborationTask> handle(GetCollaborationTaskByIdQuery query);
}
