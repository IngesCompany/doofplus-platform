package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Response resource representing an operation of a production order.
 */
public record OperationResource(
        Long id,
        int sequence,
        String name,
        String equipment,
        int progress,
        String status,
        String reference
) {
}
