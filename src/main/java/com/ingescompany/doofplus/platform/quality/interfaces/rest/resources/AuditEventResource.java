package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AuditEventResource(
        Long id,
        String aggregateType,
        Long aggregateId,
        String eventType,
        String description,
        Long performedBy,
        LocalDateTime occurredAt
) {
}