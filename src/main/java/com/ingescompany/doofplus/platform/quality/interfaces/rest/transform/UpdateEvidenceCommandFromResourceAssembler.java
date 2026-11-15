package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateEvidenceResource;

/**
 * Converts the body to replace a evidence to its command.
 */
public final class UpdateEvidenceCommandFromResourceAssembler {

    private UpdateEvidenceCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param evidenceId the identifier of the evidence
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static UpdateEvidenceCommand toCommandFromResource(Long evidenceId, UpdateEvidenceResource resource) {
        return new UpdateEvidenceCommand(
                evidenceId,
                resource.recordCode(),
                resource.fileName(),
                resource.detail(),
                QualityValueParser.toEnum(EvidenceStatus.class, resource.status(), "quality.evidence.error.status.invalid"));
    }
}
