package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateCapaPlanResource;

/**
 * Converts the body to create a capa plan to its command.
 */
public final class CreateCapaPlanCommandFromResourceAssembler {

    private CreateCapaPlanCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateCapaPlanCommand toCommandFromResource(CreateCapaPlanResource resource) {
        return new CreateCapaPlanCommand(
                resource.code(),
                resource.title(),
                resource.sourceDeviation(),
                resource.owner(),
                resource.reviewer(),
                resource.rootCause(),
                resource.acceptanceCriterion(),
                QualityValueParser.toDate(resource.effectivenessCheckAt(), "quality.capaPlan.error.effectivenessCheckAt.invalid"),
                QualityValueParser.toEnum(CapaStatus.class, resource.status(), "quality.capaPlan.error.status.invalid"));
    }
}
