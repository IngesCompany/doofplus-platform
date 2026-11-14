package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;

/**
 * Command to register a new IoT sensor.
 */
public record CreateSensorCommand(
        SensorCode code,
        DeviceId deviceId,
        String type,
        String unit,
        String equipmentCode,
        Double lowerLimit,
        Double upperLimit,
        String status,
        String assignedBatch
) {
    public CreateSensorCommand {
        if (code == null) {
            throw new IllegalArgumentException("Sensor code cannot be null");
        }
        if (deviceId == null) {
            throw new IllegalArgumentException("Device ID cannot be null");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Sensor type cannot be null or blank");
        }
    }
}
