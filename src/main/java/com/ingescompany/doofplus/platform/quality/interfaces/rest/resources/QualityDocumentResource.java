package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record QualityDocumentResource(
        Long id,
        String code,
        String title,
        String description,
        String documentType,
        String fileReference,
        String version,
        String status,
        Long ownerId,
        LocalDateTime approvedAt
) {
}