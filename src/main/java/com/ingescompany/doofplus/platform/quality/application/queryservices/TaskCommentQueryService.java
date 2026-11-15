package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTaskCommentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the task comments.
 */
public interface TaskCommentQueryService {

    /**
     * Lists the task comments, filtered when the query has a value.
     *
     * @param query the query
     * @return the task comments
     */
    List<TaskComment> handle(GetAllTaskCommentsQuery query);

    /**
     * Finds a task comment by its identifier.
     *
     * @param query the query
     * @return the task comment, if it exists
     */
    Optional<TaskComment> handle(GetTaskCommentByIdQuery query);
}
