package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;

import java.util.List;

public interface CollaborationTaskQueryService {
    List<CollaborationTask> handle(GetAllCollaborationTasksQuery query);
}