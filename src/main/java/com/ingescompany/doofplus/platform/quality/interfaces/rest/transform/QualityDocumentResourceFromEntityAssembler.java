package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.QualityDocumentResource;

/**
 * Converts a quality document to its REST resource.
 */
public final class QualityDocumentResourceFromEntityAssembler {

    private QualityDocumentResourceFromEntityAssembler() {
    }

    /**
     * Converts a quality document to its resource.
     *
     * @param qualityDocument the quality document
     * @return the resource
     */
    public static QualityDocumentResource toResourceFromEntity(QualityDocument qualityDocument) {
        return new QualityDocumentResource(
                qualityDocument.getId(),
                qualityDocument.getCode(),
                qualityDocument.getVersion(),
                qualityDocument.getTitle(),
                QualityValueParser.fromEnum(qualityDocument.getStatus()),
                qualityDocument.getAuthor(),
                qualityDocument.getTechnicalReviewer(),
                qualityDocument.getApprover(),
                QualityValueParser.fromDate(qualityDocument.getChangedAt()),
                qualityDocument.getPurpose(),
                qualityDocument.getScope(),
                qualityDocument.getProcedure(),
                qualityDocument.getRevisionSummary(),
                qualityDocument.getReviewCycleMonths());
    }
}
