package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateEvidenceResource;

/**
 * Converts the body to create a evidence to its command.
 */
public final class CreateEvidenceCommandFromResourceAssembler {

    private CreateEvidenceCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateEvidenceCommand toCommandFromResource(CreateEvidenceResource resource) {
        return new CreateEvidenceCommand(
                resource.recordCode(),
                resource.fileName(),
                resource.detail(),
                QualityValueParser.toEnum(EvidenceStatus.class, resource.status(), "quality.evidence.error.status.invalid"));
    }
}
