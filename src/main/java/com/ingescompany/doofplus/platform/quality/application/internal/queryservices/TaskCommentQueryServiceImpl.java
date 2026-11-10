package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.TaskCommentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTaskCommentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITaskCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCommentQueryServiceImpl implements TaskCommentQueryService {

    private final ITaskCommentRepository taskCommentRepository;

    public TaskCommentQueryServiceImpl(ITaskCommentRepository taskCommentRepository) {
        this.taskCommentRepository = taskCommentRepository;
    }

    @Override
    public List<TaskComment> handle(GetTaskCommentsQuery query) {
        return taskCommentRepository.findByTaskId(query.taskId());
    }
}