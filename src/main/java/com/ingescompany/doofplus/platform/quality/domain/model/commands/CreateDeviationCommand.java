package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationSeverity;

public record CreateDeviationCommand(
        String code,
        String title,
        String description,
        DeviationSeverity severity,
        Long batchId,
        Long assignedTo
) {
}