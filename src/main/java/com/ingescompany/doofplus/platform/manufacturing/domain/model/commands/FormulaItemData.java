package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.FormulaItemKind;

/**
 * Data of a line of a master formula.
 *
 * @param kind      component or parameter
 * @param name      name, for example {@code Paracetamol (API)}
 * @param role      role in the formula, for example {@code Active ingredient}
 * @param target    target, for example {@code 60.0 kg}
 * @param tolerance tolerance, for example {@code ± 2.0 %}
 * @param control   supplier or control, for example {@code QC sampling}
 */
public record FormulaItemData(FormulaItemKind kind, String name, String role, String target, String tolerance,
                              String control) {
    public FormulaItemData {
        if (kind == null) throw new IllegalArgumentException("manufacturing.error.formula.itemKind.invalid");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("manufacturing.error.formula.itemName.notBlank");
    }
}
