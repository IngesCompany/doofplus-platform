package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Response resource representing an incident of a production batch.
 */
public record IncidentResource(
        Long id,
        String description,
        String severity,
        String status,
        String recordedAt
) {
}
