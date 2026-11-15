package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TaskCommentCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTaskCommentCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITaskCommentRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the task comments.
 */
@Service
public class TaskCommentCommandServiceImpl implements TaskCommentCommandService {

    private final ITaskCommentRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the task comments
     */
    public TaskCommentCommandServiceImpl(ITaskCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<TaskComment, ApplicationError> handle(CreateTaskCommentCommand command) {
        try {
            var taskComment = new TaskComment(command);
            return Result.success(repository.save(taskComment));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("TaskComment", e.getMessage()));
        }
    }
}
