package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record RequestBatchReleaseResource(
        Long batchId,
        String batchCode,
        Long requestedBy
) {
}