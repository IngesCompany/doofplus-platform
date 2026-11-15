package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditPersistenceEntity;

/**
 * Converts the audits between the domain and the persistence entity.
 */
public final class AuditPersistenceAssembler {

    private AuditPersistenceAssembler() {
    }

    /**
     * Copies the data of the audit to a row.
     *
     * @param audit    the audit
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static AuditPersistenceEntity toEntity(Audit audit, AuditPersistenceEntity entity) {
        entity.setCode(audit.getCode());
        entity.setTitle(audit.getTitle());
        entity.setPeriod(audit.getPeriod());
        entity.setAreasReviewed(audit.getAreasReviewed());
        entity.setAreasTotal(audit.getAreasTotal());
        entity.setStatus(audit.getStatus());
        return entity;
    }

    /**
     * Rebuilds the audit of a row.
     *
     * @param entity the row
     * @return the audit
     */
    public static Audit toDomain(AuditPersistenceEntity entity) {
        return Audit.reconstitute(entity.getId(), entity.getCode(), entity.getTitle(), entity.getPeriod(), entity.getAreasReviewed(), entity.getAreasTotal(), entity.getStatus());
    }
}
