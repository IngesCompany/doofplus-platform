package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CollaborationTaskCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICollaborationTaskRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class CollaborationTaskCommandServiceImpl implements CollaborationTaskCommandService {

    private final ICollaborationTaskRepository collaborationTaskRepository;

    public CollaborationTaskCommandServiceImpl(ICollaborationTaskRepository collaborationTaskRepository) {
        this.collaborationTaskRepository = collaborationTaskRepository;
    }

    @Override
    public Result<CollaborationTask, ApplicationError> handle(CreateCollaborationTaskCommand command) {
        try {
            CollaborationTask collaborationTask = new CollaborationTask(
                    command.title(),
                    command.description(),
                    command.assignedTo(),
                    command.createdBy(),
                    command.relatedDeviationId(),
                    command.dueDate()
            );

            return Result.success(collaborationTaskRepository.save(collaborationTask));

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("CollaborationTask", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-collaboration-task", e.getMessage())
            );
        }
    }
}