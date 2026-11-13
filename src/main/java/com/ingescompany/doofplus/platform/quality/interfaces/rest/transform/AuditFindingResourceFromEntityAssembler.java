package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditFindingResource;

public class AuditFindingResourceFromEntityAssembler {

    public static AuditFindingResource toResourceFromEntity(AuditFinding entity) {
        if (entity == null) {
            return null;
        }

        return new AuditFindingResource(
                entity.getId(),
                entity.getAuditId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSeverity() != null ? entity.getSeverity().name() : null,
                entity.getRelatedDeviationId(),
                entity.getIdentifiedAt()
        );
    }
}