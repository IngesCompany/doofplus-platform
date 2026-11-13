package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CollaborationTaskResource(
        Long id,
        String title,
        String description,
        Long assignedTo,
        Long createdBy,
        Long relatedDeviationId,
        LocalDate dueDate,
        String status,
        LocalDateTime completedAt
) {
}