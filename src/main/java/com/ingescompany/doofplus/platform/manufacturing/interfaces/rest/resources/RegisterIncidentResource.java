package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Register Incident resource.
 */
public record RegisterIncidentResource(
        String description,
        String severity
) {
}
