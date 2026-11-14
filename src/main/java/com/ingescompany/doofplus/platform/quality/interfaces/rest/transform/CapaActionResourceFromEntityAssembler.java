package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaActionResource;

public class CapaActionResourceFromEntityAssembler {

    public static CapaActionResource toResourceFromEntity(CapaAction entity) {
        if (entity == null) {
            return null;
        }

        return new CapaActionResource(
                entity.getId(),
                entity.getCapaPlanId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAssignedTo(),
                entity.getDueDate(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getCompletedAt()
        );
    }
}