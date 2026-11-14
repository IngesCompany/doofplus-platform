package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record EvidenceResource(
        Long id,
        String title,
        String description,
        String type,
        String fileReference,
        Long deviationId,
        Long documentId,
        Long uploadedBy,
        String status,
        LocalDateTime uploadedAt,
        LocalDateTime verifiedAt
) {
}