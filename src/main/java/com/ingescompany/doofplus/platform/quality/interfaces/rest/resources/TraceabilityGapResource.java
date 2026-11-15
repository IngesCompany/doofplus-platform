package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Traceability gap answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record TraceabilityGapResource(
        Long id,
        String batchCode,
        String product,
        String missingRecord,
        String detail,
        String status) {
}
