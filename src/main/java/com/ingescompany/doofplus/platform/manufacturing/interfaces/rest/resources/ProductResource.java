package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Response resource representing a product.
 */
public record ProductResource(
        Long id,
        String code,
        String name,
        String dosageForm
) {
}
