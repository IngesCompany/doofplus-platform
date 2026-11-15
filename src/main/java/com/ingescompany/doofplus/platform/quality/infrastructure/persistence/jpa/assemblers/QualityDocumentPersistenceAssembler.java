package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.QualityDocumentPersistenceEntity;

/**
 * Converts the quality documents between the domain and the persistence entity.
 */
public final class QualityDocumentPersistenceAssembler {

    private QualityDocumentPersistenceAssembler() {
    }

    /**
     * Copies the data of the quality document to a row.
     *
     * @param qualityDocument    the quality document
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static QualityDocumentPersistenceEntity toEntity(QualityDocument qualityDocument, QualityDocumentPersistenceEntity entity) {
        entity.setCode(qualityDocument.getCode());
        entity.setVersion(qualityDocument.getVersion());
        entity.setTitle(qualityDocument.getTitle());
        entity.setStatus(qualityDocument.getStatus());
        entity.setAuthor(qualityDocument.getAuthor());
        entity.setTechnicalReviewer(qualityDocument.getTechnicalReviewer());
        entity.setApprover(qualityDocument.getApprover());
        entity.setChangedAt(qualityDocument.getChangedAt());
        entity.setPurpose(qualityDocument.getPurpose());
        entity.setScope(qualityDocument.getScope());
        entity.setProcedure(qualityDocument.getProcedure());
        entity.setRevisionSummary(qualityDocument.getRevisionSummary());
        entity.setReviewCycleMonths(qualityDocument.getReviewCycleMonths());
        return entity;
    }

    /**
     * Rebuilds the quality document of a row.
     *
     * @param entity the row
     * @return the quality document
     */
    public static QualityDocument toDomain(QualityDocumentPersistenceEntity entity) {
        return QualityDocument.reconstitute(entity.getId(), entity.getCode(), entity.getVersion(), entity.getTitle(), entity.getStatus(), entity.getAuthor(), entity.getTechnicalReviewer(), entity.getApprover(), entity.getChangedAt(), entity.getPurpose(), entity.getScope(), entity.getProcedure(), entity.getRevisionSummary(), entity.getReviewCycleMonths());
    }
}
