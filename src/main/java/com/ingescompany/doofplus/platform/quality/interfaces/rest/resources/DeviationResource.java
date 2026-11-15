package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Deviation answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record DeviationResource(
        Long id,
        String code,
        String title,
        String batchCode,
        String orderCode,
        String summary,
        String classification,
        String containment,
        Integer severity,
        Integer likelihood,
        Integer detectability,
        String status,
        String rootCause,
        String capaCode,
        String reportedBy,
        String reportedAt,
        String owner,
        String dueAt) {
}
