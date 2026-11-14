package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CapaActionResource(
        Long id,
        Long capaPlanId,
        String title,
        String description,
        String assignedTo,
        LocalDate dueDate,
        String status,
        LocalDateTime completedAt
) {
}