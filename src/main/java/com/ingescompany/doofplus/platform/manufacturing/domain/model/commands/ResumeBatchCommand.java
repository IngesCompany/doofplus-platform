package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to resume a production batch that is on hold.
 *
 * @param batchId identifier of the batch
 */
public record ResumeBatchCommand(Long batchId) {
    public ResumeBatchCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("BatchId cannot be null or zero");
        }
    }
}
