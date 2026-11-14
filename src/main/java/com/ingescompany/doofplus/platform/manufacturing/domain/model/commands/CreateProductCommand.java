package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.DosageForm;

/**
 * Command to register a product of the catalog.
 *
 * @param code       unique product code, for example {@code AC500}
 * @param name       name, for example {@code Paracetamol 500 mg}
 * @param dosageForm dosage form
 */
public record CreateProductCommand(String code, String name, DosageForm dosageForm) {
    public CreateProductCommand {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("manufacturing.error.product.code.notBlank");
        if (code.trim().length() > 30) throw new IllegalArgumentException("manufacturing.error.product.code.size");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("manufacturing.error.product.name.notBlank");
        if (dosageForm == null) throw new IllegalArgumentException("manufacturing.error.product.dosageForm.invalid");
        code = code.trim();
        name = name.trim();
    }
}
