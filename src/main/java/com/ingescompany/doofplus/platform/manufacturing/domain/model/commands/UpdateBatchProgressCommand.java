package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to update the progress of a production batch, as the Web App shows it.
 *
 * @param batchId  identifier of the batch
 * @param progress progress from 0 to 100
 */
public record UpdateBatchProgressCommand(Long batchId, Integer progress) {
    public UpdateBatchProgressCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("BatchId cannot be null or zero");
        }
        if (progress == null || progress < 0 || progress > 100) {
            throw new IllegalArgumentException("manufacturing.error.batch.progress.invalid");
        }
    }
}
