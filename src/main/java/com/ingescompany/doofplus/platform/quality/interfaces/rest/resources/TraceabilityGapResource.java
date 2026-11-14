package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record TraceabilityGapResource(
        Long id,
        Long batchId,
        String description,
        String missingReference,
        String affectedProcess,
        String status,
        Long reviewedBy,
        LocalDateTime detectedAt,
        LocalDateTime resolvedAt
) {
}