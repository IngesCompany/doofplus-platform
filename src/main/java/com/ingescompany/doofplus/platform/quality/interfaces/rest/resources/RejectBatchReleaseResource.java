package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record RejectBatchReleaseResource(
        Long releaseId,
        Long signedBy,
        String decision
) {
}