package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;

/**
 * Command to record a reading that ThingsBoard sends for a device (TS04).
 *
 * @param deviceId   ThingsBoard device of the sensor
 * @param value      measured value
 * @param recordedAt when it was measured (ISO-8601); now when it is empty
 */
public record RecordTelemetryCommand(DeviceId deviceId, Double value, String recordedAt) {
    public RecordTelemetryCommand {
        if (deviceId == null) throw new IllegalArgumentException("monitoring.sensor.error.deviceId.notBlank");
        if (value == null) throw new IllegalArgumentException("monitoring.telemetry.error.value.notNull");
    }
}
