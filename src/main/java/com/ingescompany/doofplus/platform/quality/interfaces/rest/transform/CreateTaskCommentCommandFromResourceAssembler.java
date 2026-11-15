package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTaskCommentCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTaskCommentResource;

/**
 * Converts the body to create a task comment to its command.
 */
public final class CreateTaskCommentCommandFromResourceAssembler {

    private CreateTaskCommentCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateTaskCommentCommand toCommandFromResource(CreateTaskCommentResource resource) {
        return new CreateTaskCommentCommand(
                resource.recordCode(),
                resource.author(),
                resource.initials(),
                QualityValueParser.toDateTime(resource.postedAt(), "quality.taskComment.error.postedAt.invalid"),
                resource.text());
    }
}
