package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to record the progress of an operation.
 */
public record UpdateOperationResource(
        Integer progress,
        String status,
        String reference
) {
}
