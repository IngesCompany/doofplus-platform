package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Create Production Batch resource.
 */
public record CreateProductionBatchResource(
        String batchNumber,
        Long productionOrderId,
        BigDecimal plannedQuantityValue,
        String plannedQuantityUnit,
        String line,
        String owner
) {
}
