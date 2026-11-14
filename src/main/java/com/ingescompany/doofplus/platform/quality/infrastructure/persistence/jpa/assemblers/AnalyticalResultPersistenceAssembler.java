package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AnalyticalResultPersistenceEntity;

public class AnalyticalResultPersistenceAssembler {

    public static AnalyticalResult toDomain(AnalyticalResultPersistenceEntity entity) {
        return AnalyticalResult.reconstitute(
                entity.getId(),
                entity.getBatchId(),
                entity.getTestName(),
                entity.getParameter(),
                entity.getMeasuredValue(),
                entity.getMinimumValue(),
                entity.getMaximumValue(),
                entity.getUnit(),
                entity.getStatus(),
                entity.getReviewedBy(),
                entity.getTestedAt(),
                entity.getReviewedAt()
        );
    }

    public static AnalyticalResultPersistenceEntity toEntity(AnalyticalResult result) {
        AnalyticalResultPersistenceEntity entity = new AnalyticalResultPersistenceEntity();

        entity.setBatchId(result.getBatchId());
        entity.setTestName(result.getTestName());
        entity.setParameter(result.getParameter());
        entity.setMeasuredValue(result.getMeasuredValue());
        entity.setMinimumValue(result.getMinimumValue());
        entity.setMaximumValue(result.getMaximumValue());
        entity.setUnit(result.getUnit());
        entity.setStatus(result.getStatus());
        entity.setReviewedBy(result.getReviewedBy());
        entity.setTestedAt(result.getTestedAt());
        entity.setReviewedAt(result.getReviewedAt());

        return entity;
    }
}