package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AuditResource(
        Long id,
        String code,
        String title,
        String scope,
        String auditType,
        Long auditorId,
        LocalDate plannedDate,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        String status
) {
}