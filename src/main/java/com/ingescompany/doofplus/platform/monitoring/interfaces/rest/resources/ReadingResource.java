package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource representation of a telemetry reading.
 */
public record ReadingResource(
        Long id,
        String sensorCode,
        String batchCode,
        Double value,
        String recordedAt,
        String source
) {
}
