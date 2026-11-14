package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateAuditResource(
        String code,
        String title,
        String scope,
        String auditType,
        Long auditorId,
        LocalDate plannedDate
) {
}