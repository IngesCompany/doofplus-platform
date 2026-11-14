package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateReadingResource;

/**
 * Assembler to translate a {@link CreateReadingResource} into a {@link CreateReadingCommand}.
 */
public final class CreateReadingCommandFromResourceAssembler {

    private CreateReadingCommandFromResourceAssembler() {
    }

    public static CreateReadingCommand toCommandFromResource(CreateReadingResource resource) {
        if (resource == null) return null;
        return new CreateReadingCommand(
                resource.sensorCode(),
                resource.batchCode(),
                resource.value(),
                resource.recordedAt(),
                resource.source()
        );
    }
}
