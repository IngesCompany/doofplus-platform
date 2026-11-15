package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.CollaborationTaskQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCollaborationTasksQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCollaborationTaskByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICollaborationTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the collaboration tasks.
 */
@Service
public class CollaborationTaskQueryServiceImpl implements CollaborationTaskQueryService {

    private final ICollaborationTaskRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the collaboration tasks
     */
    public CollaborationTaskQueryServiceImpl(ICollaborationTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CollaborationTask> handle(GetAllCollaborationTasksQuery query) {
        return query.recordCode() == null || query.recordCode().isBlank()
                ? repository.findAll()
                : repository.findByRecordCode(query.recordCode().trim());
    }

    @Override
    public Optional<CollaborationTask> handle(GetCollaborationTaskByIdQuery query) {
        return repository.findById(query.collaborationTaskId());
    }
}
