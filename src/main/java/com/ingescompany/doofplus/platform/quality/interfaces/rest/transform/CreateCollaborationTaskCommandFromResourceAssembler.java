package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCollaborationTaskCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TaskStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCollaborationTaskResource;

/**
 * Converts the body to create a collaboration task to its command.
 */
public final class CreateCollaborationTaskCommandFromResourceAssembler {

    private CreateCollaborationTaskCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateCollaborationTaskCommand toCommandFromResource(CreateCollaborationTaskResource resource) {
        return new CreateCollaborationTaskCommand(
                resource.title(),
                resource.recordCode(),
                resource.detail(),
                resource.owner(),
                QualityValueParser.toDateTime(resource.dueAt(), "quality.collaborationTask.error.dueAt.invalid"),
                QualityValueParser.toEnum(TaskStatus.class, resource.status(), "quality.collaborationTask.error.status.invalid"));
    }
}
