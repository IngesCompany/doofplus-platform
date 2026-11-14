package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response resource representing a production order.
 */
public record ProductionOrderResource(
        Long id,
        String code,
        String productCode,
        Long formulaId,
        String batchCode,
        BigDecimal plannedQuantity,
        String line,
        String status,
        String plannedStart,
        String plannedEnd,
        List<OperationResource> operations
) {
}
