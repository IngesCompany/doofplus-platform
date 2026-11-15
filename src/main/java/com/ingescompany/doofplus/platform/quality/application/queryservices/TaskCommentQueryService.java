package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentsQuery;

import java.util.List;

public interface TaskCommentQueryService {
    List<TaskComment> handle(GetTaskCommentsQuery query);

    /**
     * Gets a TaskComment by its identifier.
     *
     * @param query the query
     * @return the TaskComment, or empty when it does not exist
     */
    Optional<TaskComment> handle(GetTaskCommentByIdQuery query);
}
