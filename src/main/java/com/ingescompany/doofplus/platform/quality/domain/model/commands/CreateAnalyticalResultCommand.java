package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDateTime;

public record CreateAnalyticalResultCommand(
        Long batchId,
        String testName,
        String parameter,
        Double measuredValue,
        Double minimumValue,
        Double maximumValue,
        String unit,
        LocalDateTime testedAt
) {
}