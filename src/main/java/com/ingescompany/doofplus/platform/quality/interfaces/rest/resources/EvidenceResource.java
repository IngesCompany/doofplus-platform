package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Evidence answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record EvidenceResource(
        Long id,
        String recordCode,
        String fileName,
        String detail,
        String status) {
}
