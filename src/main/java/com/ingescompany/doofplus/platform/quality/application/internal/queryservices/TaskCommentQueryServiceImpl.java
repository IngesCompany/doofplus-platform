package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.TaskCommentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTaskCommentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITaskCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the task comments.
 */
@Service
public class TaskCommentQueryServiceImpl implements TaskCommentQueryService {

    private final ITaskCommentRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the task comments
     */
    public TaskCommentQueryServiceImpl(ITaskCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TaskComment> handle(GetAllTaskCommentsQuery query) {
        return query.recordCode() == null || query.recordCode().isBlank()
                ? repository.findAll()
                : repository.findByRecordCode(query.recordCode().trim());
    }

    @Override
    public Optional<TaskComment> handle(GetTaskCommentByIdQuery query) {
        return repository.findById(query.taskCommentId());
    }
}
