package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCollaborationTaskResource;

public class CreateCollaborationTaskCommandFromResourceAssembler {

    public static CreateCollaborationTaskCommand toCommandFromResource(
            CreateCollaborationTaskResource resource) {

        return new CreateCollaborationTaskCommand(
                resource.title(),
                resource.description(),
                resource.assignedTo(),
                resource.createdBy(),
                resource.relatedDeviationId(),
                resource.dueDate()
        );
    }
}