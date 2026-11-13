package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditFindingPersistenceEntity;

public class AuditFindingPersistenceAssembler {

    public static AuditFinding toDomain(AuditFindingPersistenceEntity entity) {
        return AuditFinding.reconstitute(
                entity.getId(),
                entity.getAuditId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSeverity(),
                entity.getRelatedDeviationId(),
                entity.getIdentifiedAt()
        );
    }

    public static AuditFindingPersistenceEntity toEntity(AuditFinding finding) {
        AuditFindingPersistenceEntity entity = new AuditFindingPersistenceEntity();

        entity.setAuditId(finding.getAuditId());
        entity.setTitle(finding.getTitle());
        entity.setDescription(finding.getDescription());
        entity.setSeverity(finding.getSeverity());
        entity.setRelatedDeviationId(finding.getRelatedDeviationId());
        entity.setIdentifiedAt(finding.getIdentifiedAt());

        return entity;
    }
}