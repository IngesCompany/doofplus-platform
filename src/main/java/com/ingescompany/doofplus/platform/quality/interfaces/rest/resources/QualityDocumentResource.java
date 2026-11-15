package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Quality document answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record QualityDocumentResource(
        Long id,
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
