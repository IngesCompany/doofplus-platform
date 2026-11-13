package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDate;

public record CreateCollaborationTaskCommand(
        String title,
        String description,
        Long assignedTo,
        Long createdBy,
        Long relatedDeviationId,
        LocalDate dueDate
) {
}