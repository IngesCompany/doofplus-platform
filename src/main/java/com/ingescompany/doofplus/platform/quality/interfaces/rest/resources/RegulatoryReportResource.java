package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Regulatory report answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record RegulatoryReportResource(
        Long id,
        String code,
        String type,
        String scope,
        String owner,
        String status,
        String template,
        String format,
        String createdAt) {
}
