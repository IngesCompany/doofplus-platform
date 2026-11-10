package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record EvidenceVerifiedEvent(
        Long evidenceId,
        Long deviationId,
        LocalDateTime occurredAt
) {
}