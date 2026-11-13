package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.QualityDocumentResource;

public class QualityDocumentResourceFromEntityAssembler {

    public static QualityDocumentResource toResourceFromEntity(QualityDocument entity) {
        if (entity == null) {
            return null;
        }

        return new QualityDocumentResource(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDocumentType(),
                entity.getFileReference(),
                entity.getVersion(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getOwnerId(),
                entity.getApprovedAt()
        );
    }
}