package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record ApproveBatchReleaseResource(
        Long signedBy,
        String signatureReference,
        String decision
) {
}