package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource representation of a monitoring alert.
 */
public record AlertResource(
        Long id,
        String code,
        String sensorCode,
        String equipmentCode,
        String severity,
        String status,
        String title,
        String detail,
        String batchCode,
        String acknowledgedBy,
        String acknowledgedAt,
        String responseNote,
        String linkedRecord
) {
}
