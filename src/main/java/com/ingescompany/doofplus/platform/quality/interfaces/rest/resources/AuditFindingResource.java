package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AuditFindingResource(
        Long id,
        Long auditId,
        String title,
        String description,
        String severity,
        Long relatedDeviationId,
        LocalDateTime identifiedAt
) {
}