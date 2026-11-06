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
    public CreateReadingCommand {
        if (sensorCode == null || sensorCode.isBlank()) {
            throw new IllegalArgumentException("Sensor code cannot be null or blank");
        }
        if (value == null) {
            throw new IllegalArgumentException("Reading value cannot be null");
        }
    }
}
