package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CollaborationTaskCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICollaborationTaskRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the collaboration tasks.
 */
@Service
public class CollaborationTaskCommandServiceImpl implements CollaborationTaskCommandService {

    private final ICollaborationTaskRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the collaboration tasks
     */
    public CollaborationTaskCommandServiceImpl(ICollaborationTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<CollaborationTask, ApplicationError> handle(CreateCollaborationTaskCommand command) {
        try {
            var collaborationTask = new CollaborationTask(command);
            return Result.success(repository.save(collaborationTask));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("CollaborationTask", e.getMessage()));
        }
    }
}
