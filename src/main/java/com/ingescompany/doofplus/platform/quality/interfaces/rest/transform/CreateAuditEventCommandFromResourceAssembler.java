package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditEventCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditEventResource;

/**
 * Converts the body to create a audit event to its command.
 */
public final class CreateAuditEventCommandFromResourceAssembler {

    private CreateAuditEventCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateAuditEventCommand toCommandFromResource(CreateAuditEventResource resource) {
        return new CreateAuditEventCommand(
                resource.eventCode(),
                QualityValueParser.toDateTime(resource.occurredAt(), "quality.auditEvent.error.occurredAt.invalid"),
                resource.actor(),
                resource.actorRole(),
                resource.event(),
                resource.record(),
                resource.result(),
                resource.field(),
                resource.before(),
                resource.after(),
                resource.reason());
    }
}
