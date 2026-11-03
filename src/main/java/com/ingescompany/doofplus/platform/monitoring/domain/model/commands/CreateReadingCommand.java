package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

/**
 * Command to record a telemetry reading.
 */
public record CreateReadingCommand(
        String sensorCode,
        String batchCode,
        Double value,
        String recordedAt,
        String source
) {
}