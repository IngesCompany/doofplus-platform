package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.DeviationPersistenceEntity;

public class DeviationPersistenceAssembler {

    public static Deviation toDomain(DeviationPersistenceEntity entity) {
        return Deviation.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getBatchId(),
                entity.getAssignedTo(),
                entity.getDetectedAt(),
                entity.getClosedAt()
        );
    }

    public static DeviationPersistenceEntity toEntity(Deviation domain) {
        DeviationPersistenceEntity entity = new DeviationPersistenceEntity();

        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setSeverity(domain.getSeverity());
        entity.setStatus(domain.getStatus());
        entity.setBatchId(domain.getBatchId());
        entity.setAssignedTo(domain.getAssignedTo());
        entity.setDetectedAt(domain.getDetectedAt());
        entity.setClosedAt(domain.getClosedAt());

        return entity;
    }
}