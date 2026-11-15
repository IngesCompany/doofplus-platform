package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateQualityDocumentResource;

/**
 * Converts the body to replace a quality document to its command.
 */
public final class UpdateQualityDocumentCommandFromResourceAssembler {

    private UpdateQualityDocumentCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param qualityDocumentId the identifier of the quality document
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static UpdateQualityDocumentCommand toCommandFromResource(Long qualityDocumentId, UpdateQualityDocumentResource resource) {
        return new UpdateQualityDocumentCommand(
                qualityDocumentId,
                resource.code(),
                resource.version(),
                resource.title(),
                QualityValueParser.toEnum(DocumentStatus.class, resource.status(), "quality.qualityDocument.error.status.invalid"),
                resource.author(),
                resource.technicalReviewer(),
                resource.approver(),
                QualityValueParser.toDate(resource.changedAt(), "quality.qualityDocument.error.changedAt.invalid"),
                resource.purpose(),
                resource.scope(),
                resource.procedure(),
                resource.revisionSummary(),
                resource.reviewCycleMonths());
    }
}
