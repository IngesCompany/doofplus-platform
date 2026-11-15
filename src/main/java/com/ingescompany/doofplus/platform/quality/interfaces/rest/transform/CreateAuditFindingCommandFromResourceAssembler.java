package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.FindingStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditFindingResource;

/**
 * Converts the body to create a audit finding to its command.
 */
public final class CreateAuditFindingCommandFromResourceAssembler {

    private CreateAuditFindingCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateAuditFindingCommand toCommandFromResource(CreateAuditFindingResource resource) {
        return new CreateAuditFindingCommand(
                resource.auditCode(),
                resource.code(),
                resource.title(),
                QualityValueParser.toEnum(Classification.class, resource.classification(), "quality.auditFinding.error.classification.invalid"),
                resource.owner(),
                QualityValueParser.toDate(resource.dueAt(), "quality.auditFinding.error.dueAt.invalid"),
                QualityValueParser.toEnum(FindingStatus.class, resource.status(), "quality.auditFinding.error.status.invalid"),
                resource.observation(),
                resource.response(),
                resource.closureRequirement());
    }
}
