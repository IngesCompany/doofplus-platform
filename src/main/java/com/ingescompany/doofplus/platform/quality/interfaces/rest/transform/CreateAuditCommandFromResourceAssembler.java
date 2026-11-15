package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditResource;

/**
 * Converts the body to create a audit to its command.
 */
public final class CreateAuditCommandFromResourceAssembler {

    private CreateAuditCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateAuditCommand toCommandFromResource(CreateAuditResource resource) {
        return new CreateAuditCommand(
                resource.code(),
                resource.title(),
                resource.period(),
                resource.areasReviewed(),
                resource.areasTotal(),
                QualityValueParser.toEnum(AuditStatus.class, resource.status(), "quality.audit.error.status.invalid"));
    }
}
