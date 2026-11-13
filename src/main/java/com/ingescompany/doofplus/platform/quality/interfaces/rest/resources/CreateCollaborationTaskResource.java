package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateCollaborationTaskResource(
        String title,
        String description,
        Long assignedTo,
        Long createdBy,
        Long relatedDeviationId,
        LocalDate dueDate
) {
}