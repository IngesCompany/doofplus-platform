package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AnalyticalResultResource;

public class AnalyticalResultResourceFromEntityAssembler {

    public static AnalyticalResultResource toResourceFromEntity(AnalyticalResult entity) {
        if (entity == null) {
            return null;
        }

        return new AnalyticalResultResource(
                entity.getId(),
                entity.getBatchId(),
                entity.getTestName(),
                entity.getParameter(),
                entity.getMeasuredValue(),
                entity.getMinimumValue(),
                entity.getMaximumValue(),
                entity.getUnit(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getReviewedBy(),
                entity.getTestedAt(),
                entity.getReviewedAt()
        );
    }
}