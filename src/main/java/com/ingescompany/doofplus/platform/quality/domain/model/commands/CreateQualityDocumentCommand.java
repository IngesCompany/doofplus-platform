package com.ingescompany.doofplus.platform.quality.domain.model.commands;

public record CreateQualityDocumentCommand(
        String code,
        String title,
        String description,
        String documentType,
        String fileReference,
        String version,
        Long ownerId
) {
}