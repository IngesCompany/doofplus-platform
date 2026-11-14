package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditResource;

public class AuditResourceFromEntityAssembler {

    public static AuditResource toResourceFromEntity(Audit entity) {
        if (entity == null) {
            return null;
        }

        return new AuditResource(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getScope(),
                entity.getAuditType(),
                entity.getAuditorId(),
                entity.getPlannedDate(),
                entity.getStartedAt(),
                entity.getCompletedAt(),
                entity.getStatus() != null ? entity.getStatus().name() : null
        );
    }
}