package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record BatchReleaseRejectedEvent(
        Long releaseId,
        Long batchId,
        Long signedBy,
        String decision,
        LocalDateTime occurredAt
) {
}