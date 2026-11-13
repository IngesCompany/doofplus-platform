package com.ingescompany.doofplus.platform.quality.domain.model.events;

import java.time.LocalDateTime;

public record BatchReleaseApprovedEvent(
        Long releaseId,
        Long batchId,
        Long signedBy,
        LocalDateTime occurredAt
) {
}