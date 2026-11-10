package com.ingescompany.doofplus.platform.quality.domain.model.commands;

public record CreateTraceabilityGapCommand(
        Long batchId,
        String description,
        String missingReference,
        String affectedProcess
) {
}