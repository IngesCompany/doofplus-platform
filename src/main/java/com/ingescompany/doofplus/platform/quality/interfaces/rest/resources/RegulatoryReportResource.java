package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegulatoryReportResource(
        Long id,
        String code,
        String title,
        String description,
        String regulatoryAuthority,
        String fileReference,
        LocalDate dueDate,
        String status,
        Long preparedBy,
        LocalDateTime submittedAt
) {
}