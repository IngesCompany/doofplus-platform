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
) {}