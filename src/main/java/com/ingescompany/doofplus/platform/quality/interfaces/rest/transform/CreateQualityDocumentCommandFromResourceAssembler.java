package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateQualityDocumentResource;

/**
 * Converts the body to create a quality document to its command.
 */
public final class CreateQualityDocumentCommandFromResourceAssembler {

    private CreateQualityDocumentCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateQualityDocumentCommand toCommandFromResource(CreateQualityDocumentResource resource) {
        return new CreateQualityDocumentCommand(
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
