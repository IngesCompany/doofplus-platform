package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TaskComment;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTaskCommentCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the task comments.
 */
public interface TaskCommentCommandService {

    /**
     * Creates a task comment.
     *
     * @param command the data of the task comment
     * @return the created task comment, or a validation error
     */
    Result<TaskComment, ApplicationError> handle(CreateTaskCommentCommand command);
}
