package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface CollaborationTaskCommandService {
    Result<CollaborationTask, ApplicationError> handle(CreateCollaborationTaskCommand command);
}