package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record AuditCompletedEvent(
        Long auditId,
        LocalDateTime occurredAt
) {
}