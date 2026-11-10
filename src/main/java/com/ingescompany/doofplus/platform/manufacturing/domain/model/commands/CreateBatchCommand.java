package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;

public record CreateBatchCommand(BatchNumber batchNumber, Long productionOrderId,Quantity quantity) {
    public CreateBatchCommand {
        if (batchNumber == null) {
            throw new IllegalArgumentException("BatchNumber cannot be null");
        }
        if (productionOrderId == null || productionOrderId <= 0) {
            throw new IllegalArgumentException("ProductionOrderId cannot be null or zero");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
    }
}
