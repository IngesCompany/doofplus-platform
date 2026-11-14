package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDate;

public record CreateAuditCommand(
        String code,
        String title,
        String scope,
        String auditType,
        Long auditorId,
        LocalDate plannedDate
) {
}