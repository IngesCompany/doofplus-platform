package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Response resource representing the consumption of a raw material lot in a production batch.
 */
public record MaterialConsumptionResource(
        Long id,
        Long rawMaterialLotId,
        BigDecimal consumedQuantityValue,
        String consumedQuantityUnit,
        String consumedAt
) {
}
