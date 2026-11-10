package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record DeviationResource(
        Long id,
        String code,
        String title,
        String description,
        String severity,
        String status,
        Long batchId,
        Long assignedTo,
        LocalDateTime detectedAt,
        LocalDateTime closedAt
) {
}