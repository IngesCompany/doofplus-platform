package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceType;

public record CreateEvidenceCommand(
        String title,
        String description,
        EvidenceType type,
        String fileReference,
        Long deviationId,
        Long documentId,
        Long uploadedBy
) {
}