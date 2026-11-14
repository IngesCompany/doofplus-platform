package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;

/**
 * Command to create a new piece of equipment.
 */
public record CreateEquipmentCommand(
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
    public CreateEquipmentCommand {
        if (code == null) {
            throw new IllegalArgumentException("Equipment code cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Equipment name cannot be null or blank");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Equipment model cannot be null or blank");
        }
        if (location == null) {
            throw new IllegalArgumentException("Equipment location cannot be null");
        }
    }
}
