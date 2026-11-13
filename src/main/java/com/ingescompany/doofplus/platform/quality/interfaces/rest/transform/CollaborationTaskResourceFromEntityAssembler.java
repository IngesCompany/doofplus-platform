package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CollaborationTaskResource;

public class CollaborationTaskResourceFromEntityAssembler {

    public static CollaborationTaskResource toResourceFromEntity(CollaborationTask entity) {
        if (entity == null) {
            return null;
        }

        return new CollaborationTaskResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAssignedTo(),
                entity.getCreatedBy(),
                entity.getRelatedDeviationId(),
                entity.getDueDate(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getCompletedAt()
        );
    }
}