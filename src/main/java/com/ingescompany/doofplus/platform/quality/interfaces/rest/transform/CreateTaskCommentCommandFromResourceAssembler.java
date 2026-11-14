package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTaskCommentCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTaskCommentResource;

public class CreateTaskCommentCommandFromResourceAssembler {

    public static CreateTaskCommentCommand toCommandFromResource(
            CreateTaskCommentResource resource) {

        return new CreateTaskCommentCommand(
                resource.taskId(),
                resource.authorId(),
                resource.content()
        );
    }
}