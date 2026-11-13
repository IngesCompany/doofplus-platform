package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.BatchReleaseResource;

public class BatchReleaseResourceFromEntityAssembler {

    public static BatchReleaseResource toResourceFromEntity(BatchRelease entity) {
        if (entity == null) {
            return null;
        }

        return new BatchReleaseResource(
                entity.getId(),
                entity.getBatchId(),
                entity.getBatchCode(),
                entity.getRequestedBy(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getDecision(),
                entity.getSignatureReference(),
                entity.getSignedBy(),
                entity.getSignedAt(),
                entity.getCreatedAt()
        );
    }
}