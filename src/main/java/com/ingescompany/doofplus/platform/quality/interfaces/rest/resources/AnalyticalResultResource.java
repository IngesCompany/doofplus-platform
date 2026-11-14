package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AnalyticalResultResource(
        Long id,
        Long batchId,
        String testName,
        String parameter,
        Double measuredValue,
        Double minimumValue,
        Double maximumValue,
        String unit,
        String status,
        Long reviewedBy,
        LocalDateTime testedAt,
        LocalDateTime reviewedAt
) {
}