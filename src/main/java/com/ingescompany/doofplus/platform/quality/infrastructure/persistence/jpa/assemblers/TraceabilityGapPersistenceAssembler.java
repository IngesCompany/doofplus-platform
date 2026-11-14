package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TraceabilityGapPersistenceEntity;

public class TraceabilityGapPersistenceAssembler {

    public static TraceabilityGap toDomain(TraceabilityGapPersistenceEntity entity) {
        return TraceabilityGap.reconstitute(
                entity.getId(),
                entity.getBatchId(),
                entity.getDescription(),
                entity.getMissingReference(),
                entity.getAffectedProcess(),
                entity.getStatus(),
                entity.getReviewedBy(),
                entity.getDetectedAt(),
                entity.getResolvedAt()
        );
    }

    public static TraceabilityGapPersistenceEntity toEntity(TraceabilityGap gap) {
        TraceabilityGapPersistenceEntity entity = new TraceabilityGapPersistenceEntity();

        entity.setId(gap.getId());
        entity.setBatchId(gap.getBatchId());
        entity.setDescription(gap.getDescription());
        entity.setMissingReference(gap.getMissingReference());
        entity.setAffectedProcess(gap.getAffectedProcess());
        entity.setStatus(gap.getStatus());
        entity.setReviewedBy(gap.getReviewedBy());
        entity.setDetectedAt(gap.getDetectedAt());
        entity.setResolvedAt(gap.getResolvedAt());

        return entity;
    }
}