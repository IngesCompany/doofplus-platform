package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDate;

public record CreateCapaActionCommand(
        Long capaPlanId,
        String title,
        String description,
        Long assignedTo,
        LocalDate dueDate
) {
}