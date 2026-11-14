package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command to write a master formula of a product. It starts as a draft.
 *
 * @param productCode code of the product
 * @param version     version, for example {@code v3.2}
 * @param batchSize   batch size
 * @param items       components and parameters
 */
public record CreateMasterFormulaCommand(String productCode, String version, BigDecimal batchSize,
                                         List<FormulaItemData> items) {
    public CreateMasterFormulaCommand {
        if (productCode == null || productCode.isBlank()) throw new IllegalArgumentException("manufacturing.error.product.code.notBlank");
        if (version == null || version.isBlank()) throw new IllegalArgumentException("manufacturing.error.formula.version.notBlank");
        if (version.trim().length() > 10) throw new IllegalArgumentException("manufacturing.error.formula.version.size");
        if (batchSize == null || batchSize.signum() <= 0) throw new IllegalArgumentException("manufacturing.error.formula.batchSize.invalid");
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("manufacturing.error.formula.items.empty");
        productCode = productCode.trim();
        version = version.trim();
        items = List.copyOf(items);
    }
}
