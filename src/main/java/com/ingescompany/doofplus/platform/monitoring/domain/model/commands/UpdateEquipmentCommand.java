package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;

/**
 * Command to update an existing piece of equipment.
 */
public record UpdateEquipmentCommand(
        Long id,
        EquipmentCode code,
        String name,
        String model,
        Facility location,
        CalibrationStatus calibrationStatus,
        String calibrationDue,
        String certificate,
        String nextMaintenance,
        String maintenanceOwner,
        String workOrder,
        String healthNote
) {
    public UpdateEquipmentCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Equipment ID must be positive");
        }
    }
}
