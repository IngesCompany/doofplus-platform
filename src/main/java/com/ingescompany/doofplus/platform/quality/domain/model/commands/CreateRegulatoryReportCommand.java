package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDate;

public record CreateRegulatoryReportCommand(
        String code,
        String title,
        String description,
        String regulatoryAuthority,
        String fileReference,
        LocalDate dueDate,
        Long preparedBy
) {
}