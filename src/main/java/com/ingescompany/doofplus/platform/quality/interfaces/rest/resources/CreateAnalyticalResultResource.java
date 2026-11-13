package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record CreateAnalyticalResultResource(
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