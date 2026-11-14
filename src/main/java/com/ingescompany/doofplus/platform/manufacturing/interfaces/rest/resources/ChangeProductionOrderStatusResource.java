package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to change the status of a production order.
 */
public record ChangeProductionOrderStatusResource(
        String status
) {
}
