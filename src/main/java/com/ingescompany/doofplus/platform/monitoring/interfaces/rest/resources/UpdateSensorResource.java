package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource to update an IoT sensor.
 */
public record UpdateSensorResource(
        String code,
        String deviceId,
        String type,
        String unit,
        String equipmentCode,
        Double lowerLimit,
        Double upperLimit,
        String status,
        String assignedBatch
) {
}
