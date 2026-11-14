package com.ingescompany.doofplus.platform.quality.domain.model.commands;

public record RequestBatchReleaseCommand(
        Long batchId,
        String batchCode,
        Long requestedBy
) {
}