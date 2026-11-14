package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Incident;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.IncidentResource;

public class IncidentResourceFromEntityAssembler {
    public static IncidentResource toResourceFromEntity(Incident entity) {
        if (entity == null) return null;
        return new IncidentResource(
                entity.getId(),
                entity.getDescription(),
                entity.getSeverity() != null ? entity.getSeverity().name() : null,
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getRecordedAt() != null ? entity.getRecordedAt().toString() : null
        );
    }
}
