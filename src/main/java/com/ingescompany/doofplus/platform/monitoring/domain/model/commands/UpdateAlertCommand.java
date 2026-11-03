package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
/**
 * Command to update a monitoring alert.
 */
public record UpdateAlertCommand(
        Long id,
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
}