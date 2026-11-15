package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Audit finding answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record AuditFindingResource(
        Long id,
        String auditCode,
        String code,
        String title,
        String classification,
        String owner,
        String dueAt,
        String status,
        String observation,
        String response,
        String closureRequirement) {
}
