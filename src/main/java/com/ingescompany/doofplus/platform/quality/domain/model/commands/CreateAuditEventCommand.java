package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to create a audit event. Null values take the defaults of the contract.
 */
public record CreateAuditEventCommand(
        String eventCode,
        LocalDateTime occurredAt,
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
