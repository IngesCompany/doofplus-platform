package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

/**
 * Value object representing a ThingsBoard device identifier.
 *
 * @param value ThingsBoard device identifier string
 */
public record DeviceId(String value) {
    public DeviceId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("monitoring.sensor.error.deviceId.notBlank");
        }
    }
}
