package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import org.jspecify.annotations.NotNull;

/**
 * Value object representing a device identifier
 */
@NullMMarked
public record DeviceId(Long deviceId) {
    /**
     * Compact constructor
     */
    public DeviceId {
        if (deviceId == null || deviceId < 1) {
            throw new IllegalArgumentException("Device id cannot be null or less than 1");
        }
    }
}