package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource representation of an IoT sensor.
 */
public record SensorResource(
        Long id,
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
