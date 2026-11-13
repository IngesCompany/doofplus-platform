package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record CapaPlanCreatedEvent(
        Long capaPlanId,
        Long deviationId,
        LocalDateTime occurredAt
) {
}