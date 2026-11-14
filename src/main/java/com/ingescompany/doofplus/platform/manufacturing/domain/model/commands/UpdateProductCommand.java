package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.DosageForm;

/**
 * Command to update the name and the dosage form of a product.
 *
 * @param productId  product identifier
 * @param name       new name
 * @param dosageForm new dosage form
 */
public record UpdateProductCommand(Long productId, String name, DosageForm dosageForm) {
    public UpdateProductCommand {
        if (productId == null || productId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("manufacturing.error.product.name.notBlank");
        if (dosageForm == null) throw new IllegalArgumentException("manufacturing.error.product.dosageForm.invalid");
        name = name.trim();
    }
}
