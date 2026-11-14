package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaPlanResource;

public class CreateCapaPlanCommandFromResourceAssembler {

    public static CreateCapaPlanCommand toCommandFromResource(
            CreateCapaPlanResource resource) {

        return new CreateCapaPlanCommand(
                resource.deviationId(),
                resource.title(),
                resource.description(),
                resource.dueDate(),
                resource.responsible()
        );
    }
}