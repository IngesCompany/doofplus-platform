package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record ApproveBatchReleaseResource(
        Long releaseId,
        Long signedBy,
        String signatureReference,
        String decision
) {
}