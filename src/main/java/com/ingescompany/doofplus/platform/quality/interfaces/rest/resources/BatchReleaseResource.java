package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record BatchReleaseResource(
        Long id,
        Long batchId,
        String batchCode,
        String requestedBy,
        String status,
        String decision,
        String signatureReference,
        String signedBy,
        LocalDateTime signedAt,
        LocalDateTime createdAt
) {
}