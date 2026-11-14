package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CapaPlanResource(
        Long id,
        Long deviationId,
        String title,
        String description,
        String status,
        LocalDate dueDate,
        String responsible,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
}