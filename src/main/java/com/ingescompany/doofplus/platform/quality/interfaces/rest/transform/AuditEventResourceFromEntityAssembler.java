package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditEventResource;

public class AuditEventResourceFromEntityAssembler {

    public static AuditEventResource toResourceFromEntity(AuditEvent entity) {
        if (entity == null) {
            return null;
        }

        return new AuditEventResource(
                entity.getId(),
                entity.getAggregateType(),
                entity.getAggregateId(),
                entity.getEventType(),
                entity.getDescription(),
                entity.getPerformedBy(),
                entity.getOccurredAt()
        );
    }
}