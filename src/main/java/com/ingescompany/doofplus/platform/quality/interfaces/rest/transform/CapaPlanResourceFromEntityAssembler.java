package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaPlanResource;

public class CapaPlanResourceFromEntityAssembler {

    public static CapaPlanResource toResourceFromEntity(CapaPlan entity) {
        if (entity == null) {
            return null;
        }

        return new CapaPlanResource(
                entity.getId(),
                entity.getDeviationId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getDueDate(),
                entity.getResponsible(),
                entity.getCreatedAt(),
                entity.getCompletedAt()
        );
    }
}