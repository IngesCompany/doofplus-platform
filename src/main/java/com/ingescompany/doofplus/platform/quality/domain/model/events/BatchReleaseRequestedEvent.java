package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record BatchReleaseRequestedEvent(
        Long releaseId,
        Long batchId,
        Long requestedBy,
        LocalDateTime occurredAt
) {
}