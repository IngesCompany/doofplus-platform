package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditEventResource;

/**
 * Converts a audit event to its REST resource.
 */
public final class AuditEventResourceFromEntityAssembler {

    private AuditEventResourceFromEntityAssembler() {
    }

    /**
     * Converts a audit event to its resource.
     *
     * @param auditEvent the audit event
     * @return the resource
     */
    public static AuditEventResource toResourceFromEntity(AuditEvent auditEvent) {
        return new AuditEventResource(
                auditEvent.getId(),
                auditEvent.getEventCode(),
                QualityValueParser.fromDateTime(auditEvent.getOccurredAt()),
                auditEvent.getActor(),
                auditEvent.getActorRole(),
                auditEvent.getEvent(),
                auditEvent.getRecord(),
                auditEvent.getResult(),
                auditEvent.getField(),
                auditEvent.getBefore(),
                auditEvent.getAfter(),
                auditEvent.getReason());
    }
}
