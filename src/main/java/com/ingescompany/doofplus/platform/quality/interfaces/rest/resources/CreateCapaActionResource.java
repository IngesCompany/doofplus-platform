package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateCapaActionResource(
        Long capaPlanId,
        String title,
        String description,
        Long assignedTo,
        LocalDate dueDate
) {
}