package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateDeviationResource;

/**
 * Converts the body to replace a deviation to its command.
 */
public final class UpdateDeviationCommandFromResourceAssembler {

    private UpdateDeviationCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param deviationId the identifier of the deviation
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static UpdateDeviationCommand toCommandFromResource(Long deviationId, UpdateDeviationResource resource) {
        return new UpdateDeviationCommand(
                deviationId,
                resource.code(),
                resource.title(),
                resource.batchCode(),
                resource.orderCode(),
                resource.summary(),
                QualityValueParser.toEnum(Classification.class, resource.classification(), "quality.deviation.error.classification.invalid"),
                resource.containment(),
                resource.severity(),
                resource.likelihood(),
                resource.detectability(),
                QualityValueParser.toEnum(DeviationStatus.class, resource.status(), "quality.deviation.error.status.invalid"),
                resource.rootCause(),
                resource.capaCode(),
                resource.reportedBy(),
                QualityValueParser.toDateTime(resource.reportedAt(), "quality.deviation.error.reportedAt.invalid"),
                resource.owner(),
                QualityValueParser.toDateTime(resource.dueAt(), "quality.deviation.error.dueAt.invalid"));
    }
}
