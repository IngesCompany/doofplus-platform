package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.CollaborationTaskQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICollaborationTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaborationTaskQueryServiceImpl implements CollaborationTaskQueryService {

    private final ICollaborationTaskRepository collaborationTaskRepository;

    public CollaborationTaskQueryServiceImpl(ICollaborationTaskRepository collaborationTaskRepository) {
        this.collaborationTaskRepository = collaborationTaskRepository;
    }

    @Override
    public List<CollaborationTask> handle(GetAllCollaborationTasksQuery query) {
        return collaborationTaskRepository.findAll();
    }
}