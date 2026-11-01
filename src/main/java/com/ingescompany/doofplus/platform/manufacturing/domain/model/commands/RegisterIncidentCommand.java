package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;

public record RegisterIncidentCommand(Long batchId, String description, IncidentSeverity severity) {
    public RegisterIncidentCommand {
        if (batchId == null || batchId <= 0) {
            throw new IllegalArgumentException("BatchId cannot be null or zero");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (severity == null) {
            throw new IllegalArgumentException("Severity cannot be null");
        }
    }
}
