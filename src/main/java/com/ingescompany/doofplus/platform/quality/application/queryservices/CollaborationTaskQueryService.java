package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCollaborationTaskByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;

import java.util.List;

public interface CollaborationTaskQueryService {
    List<CollaborationTask> handle(GetAllCollaborationTasksQuery query);

    /**
     * Gets a CollaborationTask by its identifier.
     *
     * @param query the query
     * @return the CollaborationTask, or empty when it does not exist
     */
    Optional<CollaborationTask> handle(GetCollaborationTaskByIdQuery query);
}
