package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Response resource representing an event of the timeline of a production batch.
 */
public record BatchEventResource(
        Long id,
        String type,
        String description,
        Long performedBy,
        String occurredAt
) {
}
