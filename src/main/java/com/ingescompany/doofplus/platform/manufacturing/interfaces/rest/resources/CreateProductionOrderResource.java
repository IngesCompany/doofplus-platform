package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request resource to plan a production order. Dates use ISO-8601.
 */
public record CreateProductionOrderResource(
        String code,
        Long formulaId,
        String batchCode,
        BigDecimal plannedQuantity,
        String line,
        String plannedStart,
        String plannedEnd,
        List<CreateOperationResource> operations
) {
}
