package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Capa action answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record CapaActionResource(
        Long id,
        String capaCode,
        String code,
        String type,
        String title,
        String owner,
        String dueAt,
        String status,
        String evidence,
        Boolean evidenceAttached) {
}
