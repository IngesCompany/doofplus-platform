package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDate;

public record CreateCapaPlanCommand(
        Long deviationId,
        String title,
        String description,
        LocalDate dueDate,
        Long responsible
) {
}