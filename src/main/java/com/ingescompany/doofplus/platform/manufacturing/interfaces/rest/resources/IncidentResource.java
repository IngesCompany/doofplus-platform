package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Incident resource.
 */
public record IncidentResource(
        Long id,
        String description,
        String severity,
        String status
) {
}
