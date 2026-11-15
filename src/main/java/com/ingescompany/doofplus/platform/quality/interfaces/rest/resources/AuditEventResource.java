package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Audit event answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record AuditEventResource(
        Long id,
        String eventCode,
        String occurredAt,
        String actor,
        String actorRole,
        String event,
        String record,
        String result,
        String field,
        String before,
        String after,
        String reason) {
}
