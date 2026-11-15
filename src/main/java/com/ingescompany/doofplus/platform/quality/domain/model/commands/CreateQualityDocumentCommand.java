package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import java.time.LocalDate;

/**
 * Command to create a quality document. Null values take the defaults of the contract.
 */
public record CreateQualityDocumentCommand(
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
