package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response resource representing a master formula.
 */
public record MasterFormulaResource(
        Long id,
        String productCode,
        String version,
        String status,
        String approvedBy,
        String approvedAt,
        BigDecimal batchSize,
        List<FormulaItemResource> items
) {
}
