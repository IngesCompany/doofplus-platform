package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Analytical result answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record AnalyticalResultResource(
        Long id,
        String batchCode,
        String sampleCode,
        String reportCode,
        String test,
        String protocol,
        String result,
        String specification,
        String analyst,
        String status,
        String approval) {
}
