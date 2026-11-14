package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to start a planned production batch.
 *
 * @param batchId identifier of the batch
 */
public record StartBatchCommand(Long batchId) {
    public StartBatchCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("BatchId cannot be null or zero");
        }
    }
}
