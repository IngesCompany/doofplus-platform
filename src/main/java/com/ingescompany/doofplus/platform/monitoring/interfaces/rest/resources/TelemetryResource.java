package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Reading that ThingsBoard sends for a device.
 *
 * @param deviceId   ThingsBoard device of the sensor
 * @param value      measured value
 * @param recordedAt when it was measured (ISO-8601, optional)
 */
public record TelemetryResource(String deviceId, Double value, String recordedAt) {
}
