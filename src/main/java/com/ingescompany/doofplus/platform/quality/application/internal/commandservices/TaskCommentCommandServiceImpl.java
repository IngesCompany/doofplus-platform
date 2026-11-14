package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TaskCommentCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTaskCommentCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITaskCommentRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class TaskCommentCommandServiceImpl implements TaskCommentCommandService {

    private final ITaskCommentRepository taskCommentRepository;

    public TaskCommentCommandServiceImpl(ITaskCommentRepository taskCommentRepository) {
        this.taskCommentRepository = taskCommentRepository;
    }

    @Override
    public Result<TaskComment, ApplicationError> handle(CreateTaskCommentCommand command) {
        try {
            TaskComment taskComment = new TaskComment(
                    command.taskId(),
                    command.authorId(),
                    command.content()
            );

            return Result.success(taskCommentRepository.save(taskComment));
        } catch (IllegalArgumentException exception) {
            return Result.failure(
                    ApplicationError.validationError("task comment", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("creating task comment", exception.getMessage())
            );
        }
    }
}