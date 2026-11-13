package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record CreateDeviationResource(
        String code,
        String title,
        String description,
        String severity,
        Long batchId,
        Long assignedTo
) {
}