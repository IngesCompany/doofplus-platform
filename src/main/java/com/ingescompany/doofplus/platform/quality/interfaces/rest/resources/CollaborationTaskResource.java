package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Collaboration task answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record CollaborationTaskResource(
        Long id,
        String title,
        String recordCode,
        String detail,
        String owner,
        String dueAt,
        String status) {
}
