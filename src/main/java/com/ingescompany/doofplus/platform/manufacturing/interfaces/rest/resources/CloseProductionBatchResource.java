package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Close Production Batch resource.
 */
public record CloseProductionBatchResource(
        BigDecimal manufacturedQuantityValue,
        String manufacturedQuantityUnit
) {
}
