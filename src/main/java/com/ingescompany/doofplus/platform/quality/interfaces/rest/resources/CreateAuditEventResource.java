package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a audit event. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateAuditEventResource(
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
