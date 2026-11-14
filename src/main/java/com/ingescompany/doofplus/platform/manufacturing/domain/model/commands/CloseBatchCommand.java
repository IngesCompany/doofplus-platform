package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;

public record CloseBatchCommand(Long batchId, Quantity manufacturedQuantity) {
    public CloseBatchCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("BatchId cannot be null or zero");
        }
        if (manufacturedQuantity == null) {
            throw new IllegalArgumentException("ManufacturedQuantity cannot be null");
        }
    }
}
