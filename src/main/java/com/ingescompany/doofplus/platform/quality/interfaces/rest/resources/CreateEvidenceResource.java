package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record CreateEvidenceResource(
        String title,
        String description,
        String type,
        String fileReference,
        Long deviationId,
        Long documentId,
        Long uploadedBy
) {
}