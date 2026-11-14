package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;

/**
 * Command to create a monitoring alert.
 */
public record CreateAlertCommand(
        AlertCode code,
        String sensorCode,
        String equipmentCode,
        String severity,
        String status,
        String title,
        String detail,
        String batchCode,
        String acknowledgedBy,
        String acknowledgedAt,
        String responseNote,
        String linkedRecord
) {
    public CreateAlertCommand {
        if (code == null) {
            throw new IllegalArgumentException("Alert code cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Alert title cannot be null or blank");
        }
    }
}
