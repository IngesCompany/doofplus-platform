package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to register a product.
 */
public record CreateProductResource(
        String code,
        String name,
        String dosageForm
) {
}
