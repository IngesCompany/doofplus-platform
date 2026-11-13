package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationResource;

public class DeviationResourceFromEntityAssembler {

    public static DeviationResource toResourceFromEntity(Deviation entity) {
        if (entity == null) {
            return null;
        }

        return new DeviationResource(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSeverity() != null ? entity.getSeverity().name() : null,
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getBatchId(),
                entity.getAssignedTo(),
                entity.getDetectedAt(),
                entity.getClosedAt()
        );
    }
}