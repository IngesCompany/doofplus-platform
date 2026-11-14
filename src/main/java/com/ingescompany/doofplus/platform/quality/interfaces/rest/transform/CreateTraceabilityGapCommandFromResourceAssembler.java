package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTraceabilityGapResource;

public class CreateTraceabilityGapCommandFromResourceAssembler {

    public static CreateTraceabilityGapCommand toCommandFromResource(
            CreateTraceabilityGapResource resource) {

        return new CreateTraceabilityGapCommand(
                resource.batchId(),
                resource.description(),
                resource.missingReference(),
                resource.affectedProcess()
        );
    }
}