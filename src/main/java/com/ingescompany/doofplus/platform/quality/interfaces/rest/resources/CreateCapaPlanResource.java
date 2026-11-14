package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateCapaPlanResource(
        Long deviationId,
        String title,
        String description,
        LocalDate dueDate,
        Long responsible
) {
}