package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource to create an IoT sensor.
 */
public record CreateSensorResource(
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
