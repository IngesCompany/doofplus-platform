package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditFindingPersistenceEntity;

/**
 * Converts the audit findings between the domain and the persistence entity.
 */
public final class AuditFindingPersistenceAssembler {

    private AuditFindingPersistenceAssembler() {
    }

    /**
     * Copies the data of the audit finding to a row.
     *
     * @param auditFinding    the audit finding
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static AuditFindingPersistenceEntity toEntity(AuditFinding auditFinding, AuditFindingPersistenceEntity entity) {
        entity.setAuditCode(auditFinding.getAuditCode());
        entity.setCode(auditFinding.getCode());
        entity.setTitle(auditFinding.getTitle());
        entity.setClassification(auditFinding.getClassification());
        entity.setOwner(auditFinding.getOwner());
        entity.setDueAt(auditFinding.getDueAt());
        entity.setStatus(auditFinding.getStatus());
        entity.setObservation(auditFinding.getObservation());
        entity.setResponse(auditFinding.getResponse());
        entity.setClosureRequirement(auditFinding.getClosureRequirement());
        return entity;
    }

    /**
     * Rebuilds the audit finding of a row.
     *
     * @param entity the row
     * @return the audit finding
     */
    public static AuditFinding toDomain(AuditFindingPersistenceEntity entity) {
        return AuditFinding.reconstitute(entity.getId(), entity.getAuditCode(), entity.getCode(), entity.getTitle(), entity.getClassification(), entity.getOwner(), entity.getDueAt(), entity.getStatus(), entity.getObservation(), entity.getResponse(), entity.getClosureRequirement());
    }
}
