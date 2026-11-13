package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationSeverity;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateDeviationResource;

public class CreateDeviationCommandFromResourceAssembler {

    public static CreateDeviationCommand toCommandFromResource(
            CreateDeviationResource resource) {

        return new CreateDeviationCommand(
                resource.code(),
                resource.title(),
                resource.description(),
                DeviationSeverity.valueOf(resource.severity().toUpperCase()),
                resource.batchId(),
                resource.assignedTo()
        );
    }
}