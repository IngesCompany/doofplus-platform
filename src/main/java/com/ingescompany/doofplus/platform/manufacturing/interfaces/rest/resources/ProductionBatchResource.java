package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Production Batch resource.
 */
public record ProductionBatchResource(
        Long id,
        String batchNumber,
        Long productionOrderId,
        String status,
        BigDecimal plannedQuantityValue,
        String plannedQuantityUnit,
        BigDecimal manufacturedQuantityValue,
        String manufacturedQuantityUnit,
        String startedAt,
        String finishedAt
) {
}
