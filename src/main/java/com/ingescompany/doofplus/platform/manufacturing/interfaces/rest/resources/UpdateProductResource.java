package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to update a product.
 */
public record UpdateProductResource(
        String name,
        String dosageForm
) {
}
