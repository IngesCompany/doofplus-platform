package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to release a production batch for distribution, signed by QA.
 *
 * @param batchId    identifier of the batch
 * @param releasedBy name of the person who releases the batch
 */
public record ReleaseBatchCommand(Long batchId, String releasedBy) {
    public ReleaseBatchCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("BatchId cannot be null or zero");
        }
        if (releasedBy == null || releasedBy.isBlank()) {
            throw new IllegalArgumentException("manufacturing.error.batch.releasedBy.notBlank");
        }
    }
}
