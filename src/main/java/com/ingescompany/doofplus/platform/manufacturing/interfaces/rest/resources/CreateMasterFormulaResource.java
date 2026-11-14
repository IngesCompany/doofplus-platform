package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request resource to write a master formula.
 */
public record CreateMasterFormulaResource(
        String productCode,
        String version,
        BigDecimal batchSize,
        List<CreateFormulaItemResource> items
) {
}
