package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;

/**
 * Command to update an IoT sensor.
 */
public record UpdateSensorCommand(
        Long id,
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
    public UpdateSensorCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Sensor ID must be positive");
        }
    }
}
