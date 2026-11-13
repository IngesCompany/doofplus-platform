package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditPersistenceEntity;

public class AuditPersistenceAssembler {

    public static Audit toDomain(AuditPersistenceEntity entity) {
        return Audit.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getScope(),
                entity.getAuditType(),
                entity.getAuditorId(),
                entity.getPlannedDate(),
                entity.getStartedAt(),
                entity.getCompletedAt(),
                entity.getStatus()
        );
    }

    public static AuditPersistenceEntity toEntity(Audit audit) {
        AuditPersistenceEntity entity = new AuditPersistenceEntity();

        entity.setCode(audit.getCode());
        entity.setTitle(audit.getTitle());
        entity.setScope(audit.getScope());
        entity.setAuditType(audit.getAuditType());
        entity.setAuditorId(audit.getAuditorId());
        entity.setPlannedDate(audit.getPlannedDate());
        entity.setStartedAt(audit.getStartedAt());
        entity.setCompletedAt(audit.getCompletedAt());
        entity.setStatus(audit.getStatus());

        return entity;
    }
}