package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateRegulatoryReportResource(
        String code,
        String title,
        String description,
        String regulatoryAuthority,
        String fileReference,
        LocalDate dueDate,
        Long preparedBy
) {
}