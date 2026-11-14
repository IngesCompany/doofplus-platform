package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record DeviationCreatedEvent(
        Long deviationId,
        String code,
        Long batchId,
        LocalDateTime occurredAt
) {
}