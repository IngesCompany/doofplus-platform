package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditFindingSeverity;

public record CreateAuditFindingCommand(
        Long auditId,
        String title,
        String description,
        AuditFindingSeverity severity,
        Long relatedDeviationId
) {
}