package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to update a quality document. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record UpdateQualityDocumentResource(
        String code,
        String version,
        String title,
        String status,
        String author,
        String technicalReviewer,
        String approver,
        String changedAt,
        String purpose,
        String scope,
        String procedure,
        String revisionSummary,
        Integer reviewCycleMonths) {
}
