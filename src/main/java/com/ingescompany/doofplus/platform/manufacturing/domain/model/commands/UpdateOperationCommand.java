package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.OperationStatus;

/**
 * Command to record the progress of an operation of a production order.
 *
 * @param productionOrderId order identifier
 * @param sequence          sequence of the operation
 * @param progress          progress from 0 to 100
 * @param status            status of the operation
 * @param reference         reference, for example the deviation that stopped it
 */
public record UpdateOperationCommand(Long productionOrderId, Integer sequence, Integer progress,
                                     OperationStatus status, String reference) {
    public UpdateOperationCommand {
        if (productionOrderId == null || productionOrderId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
        if (sequence == null || sequence <= 0) throw new IllegalArgumentException("manufacturing.error.order.sequence.invalid");
        if (progress == null || progress < 0 || progress > 100) throw new IllegalArgumentException("manufacturing.error.order.progress.invalid");
        if (status == null) throw new IllegalArgumentException("manufacturing.error.order.operationStatus.invalid");
    }
}
