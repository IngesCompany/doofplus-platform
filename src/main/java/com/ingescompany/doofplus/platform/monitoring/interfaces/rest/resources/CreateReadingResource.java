package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource to record a telemetry reading.
 */
public record CreateReadingResource(
        String sensorCode,
        String batchCode,
        Double value,
        String recordedAt,
        String source
) {
}
