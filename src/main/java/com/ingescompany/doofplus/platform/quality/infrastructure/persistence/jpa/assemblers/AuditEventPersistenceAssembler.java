package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditEventPersistenceEntity;

/**
 * Converts the audit events between the domain and the persistence entity.
 */
public final class AuditEventPersistenceAssembler {

    private AuditEventPersistenceAssembler() {
    }

    /**
     * Copies the data of the audit event to a row.
     *
     * @param auditEvent    the audit event
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static AuditEventPersistenceEntity toEntity(AuditEvent auditEvent, AuditEventPersistenceEntity entity) {
        entity.setEventCode(auditEvent.getEventCode());
        entity.setOccurredAt(auditEvent.getOccurredAt());
        entity.setActor(auditEvent.getActor());
        entity.setActorRole(auditEvent.getActorRole());
        entity.setEvent(auditEvent.getEvent());
        entity.setRecord(auditEvent.getRecord());
        entity.setResult(auditEvent.getResult());
        entity.setField(auditEvent.getField());
        entity.setBefore(auditEvent.getBefore());
        entity.setAfter(auditEvent.getAfter());
        entity.setReason(auditEvent.getReason());
        return entity;
    }

    /**
     * Rebuilds the audit event of a row.
     *
     * @param entity the row
     * @return the audit event
     */
    public static AuditEvent toDomain(AuditEventPersistenceEntity entity) {
        return AuditEvent.reconstitute(entity.getId(), entity.getEventCode(), entity.getOccurredAt(), entity.getActor(), entity.getActorRole(), entity.getEvent(), entity.getRecord(), entity.getResult(), entity.getField(), entity.getBefore(), entity.getAfter(), entity.getReason());
    }
}
