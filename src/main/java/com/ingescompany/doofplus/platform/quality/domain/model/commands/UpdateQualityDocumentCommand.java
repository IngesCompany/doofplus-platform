package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import java.time.LocalDate;

/**
 * Command to replace all the data of a quality document.
 *
 * @param qualityDocumentId the identifier of the quality document
 */
public record UpdateQualityDocumentCommand(
        Long qualityDocumentId,
        String code,
        String version,
        String title,
        DocumentStatus status,
        String author,
        String technicalReviewer,
        String approver,
        LocalDate changedAt,
        String purpose,
        String scope,
        String procedure,
        String revisionSummary,
        Integer reviewCycleMonths) {
}
