package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditEventPersistenceEntity;

public class AuditEventPersistenceAssembler {

    public static AuditEvent toDomain(AuditEventPersistenceEntity entity) {
        return AuditEvent.reconstitute(
                entity.getId(),
                entity.getAggregateType(),
                entity.getAggregateId(),
                entity.getEventType(),
                entity.getDescription(),
                entity.getPerformedBy(),
                entity.getOccurredAt()
        );
    }

    public static AuditEventPersistenceEntity toEntity(AuditEvent event) {
        AuditEventPersistenceEntity entity = new AuditEventPersistenceEntity();

        entity.setAggregateType(event.getAggregateType());
        entity.setAggregateId(event.getAggregateId());
        entity.setEventType(event.getEventType());
        entity.setDescription(event.getDescription());
        entity.setPerformedBy(event.getPerformedBy());
        entity.setOccurredAt(event.getOccurredAt());

        return entity;
    }
}