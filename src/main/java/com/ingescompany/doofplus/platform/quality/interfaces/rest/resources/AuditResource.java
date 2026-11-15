package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Audit answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record AuditResource(
        Long id,
        String code,
        String title,
        String period,
        Integer areasReviewed,
        Integer areasTotal,
        String status) {
}
