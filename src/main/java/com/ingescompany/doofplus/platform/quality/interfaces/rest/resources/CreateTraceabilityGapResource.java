package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record CreateTraceabilityGapResource(
        Long batchId,
        String description,
        String missingReference,
        String affectedProcess
) {
}