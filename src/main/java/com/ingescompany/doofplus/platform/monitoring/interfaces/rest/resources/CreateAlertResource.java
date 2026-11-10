package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource to create a monitoring alert.
 */
public record CreateAlertResource(
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
