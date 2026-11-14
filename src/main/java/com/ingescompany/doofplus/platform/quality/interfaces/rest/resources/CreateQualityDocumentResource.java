package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record CreateQualityDocumentResource(
        String code,
        String title,
        String description,
        String documentType,
        String fileReference,
        String version,
        Long ownerId
) {
}