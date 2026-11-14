package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record CreateAuditFindingResource(
        Long auditId,
        String title,
        String description,
        String severity,
        Long relatedDeviationId
) {
}