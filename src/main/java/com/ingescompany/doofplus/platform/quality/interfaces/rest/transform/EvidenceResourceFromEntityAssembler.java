package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.EvidenceResource;

public class EvidenceResourceFromEntityAssembler {

    public static EvidenceResource toResourceFromEntity(Evidence entity) {
        if (entity == null) {
            return null;
        }

        return new EvidenceResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType() != null ? entity.getType().name() : null,
                entity.getFileReference(),
                entity.getDeviationId(),
                entity.getDocumentId(),
                entity.getUploadedBy(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getUploadedAt(),
                entity.getVerifiedAt()
        );
    }
}