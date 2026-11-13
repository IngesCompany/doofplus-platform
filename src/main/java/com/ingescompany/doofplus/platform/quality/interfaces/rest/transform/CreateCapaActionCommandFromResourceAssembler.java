package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaActionResource;

public class CreateCapaActionCommandFromResourceAssembler {

    public static CreateCapaActionCommand toCommandFromResource(
            CreateCapaActionResource resource) {

        return new CreateCapaActionCommand(
                resource.capaPlanId(),
                resource.title(),
                resource.description(),
                resource.assignedTo(),
                resource.dueDate()
        );
    }
}