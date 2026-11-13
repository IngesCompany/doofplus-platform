package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TraceabilityGapResource;

public class TraceabilityGapResourceFromEntityAssembler {

    public static TraceabilityGapResource toResourceFromEntity(TraceabilityGap entity) {
        if (entity == null) {
            return null;
        }

        return new TraceabilityGapResource(
                entity.getId(),
                entity.getBatchId(),
                entity.getDescription(),
                entity.getMissingReference(),
                entity.getAffectedProcess(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getReviewedBy(),
                entity.getDetectedAt(),
                entity.getResolvedAt()
        );
    }
}