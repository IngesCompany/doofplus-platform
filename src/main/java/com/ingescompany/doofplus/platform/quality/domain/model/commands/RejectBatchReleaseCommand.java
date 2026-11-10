package com.ingescompany.doofplus.platform.quality.domain.model.commands;

public record RejectBatchReleaseCommand(
        Long releaseId,
        Long signedBy,
        String decision
) {
}