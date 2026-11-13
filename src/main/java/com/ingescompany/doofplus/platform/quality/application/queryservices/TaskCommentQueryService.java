package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentsQuery;

import java.util.List;

public interface TaskCommentQueryService {
    List<TaskComment> handle(GetTaskCommentsQuery query);
}