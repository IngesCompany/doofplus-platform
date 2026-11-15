package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.GapStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateTraceabilityGapResource;

/**
 * Converts the body to create a traceability gap to its command.
 */
public final class CreateTraceabilityGapCommandFromResourceAssembler {

    private CreateTraceabilityGapCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateTraceabilityGapCommand toCommandFromResource(CreateTraceabilityGapResource resource) {
        return new CreateTraceabilityGapCommand(
                resource.batchCode(),
                resource.product(),
                resource.missingRecord(),
                resource.detail(),
                QualityValueParser.toEnum(GapStatus.class, resource.status(), "quality.traceabilityGap.error.status.invalid"));
    }
}
