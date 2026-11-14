package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.ProductionOrderStatus;

/**
 * Command to change the status of a production order.
 *
 * @param productionOrderId order identifier
 * @param status            new status
 */
public record ChangeProductionOrderStatusCommand(Long productionOrderId, ProductionOrderStatus status) {
    public ChangeProductionOrderStatusCommand {
        if (productionOrderId == null || productionOrderId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
        if (status == null) throw new IllegalArgumentException("manufacturing.error.order.status.invalid");
    }
}
