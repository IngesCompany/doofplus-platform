package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource of an operation of a production order.
 */
public record CreateOperationResource(
        Integer sequence,
        String name,
        String equipment
) {
}
