package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the collaboration tasks.
 */
public interface CollaborationTaskCommandService {

    /**
     * Creates a collaboration task.
     *
     * @param command the data of the collaboration task
     * @return the created collaboration task, or a validation error
     */
    Result<CollaborationTask, ApplicationError> handle(CreateCollaborationTaskCommand command);
}
