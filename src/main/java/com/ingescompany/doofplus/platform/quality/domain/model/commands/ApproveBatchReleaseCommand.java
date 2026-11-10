package com.ingescompany.doofplus.platform.quality.domain.model.commands;

public record ApproveBatchReleaseCommand(
        Long releaseId,
        Long signedBy,
        String signatureReference,
        String decision
) {
}