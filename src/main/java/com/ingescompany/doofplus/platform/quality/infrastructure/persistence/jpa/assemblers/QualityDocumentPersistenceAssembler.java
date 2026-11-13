package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.QualityDocumentPersistenceEntity;

public class QualityDocumentPersistenceAssembler {

    public static QualityDocument toDomain(QualityDocumentPersistenceEntity entity) {
        return QualityDocument.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDocumentType(),
                entity.getFileReference(),
                entity.getVersion(),
                entity.getStatus(),
                entity.getOwnerId(),
                entity.getApprovedAt()
        );
    }

    public static QualityDocumentPersistenceEntity toEntity(QualityDocument document) {
        QualityDocumentPersistenceEntity entity = new QualityDocumentPersistenceEntity();

        entity.setCode(document.getCode());
        entity.setTitle(document.getTitle());
        entity.setDescription(document.getDescription());
        entity.setDocumentType(document.getDocumentType());
        entity.setFileReference(document.getFileReference());
        entity.setVersion(document.getVersion());
        entity.setStatus(document.getStatus());
        entity.setOwnerId(document.getOwnerId());
        entity.setApprovedAt(document.getApprovedAt());

        return entity;
    }
}