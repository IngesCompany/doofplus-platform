package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateEquipmentResource;

/**
 * Assembler to translate a {@link CreateEquipmentResource} into a {@link CreateEquipmentCommand}.
 */
public final class CreateEquipmentCommandFromResourceAssembler {

    private CreateEquipmentCommandFromResourceAssembler() {
    }

    public static CreateEquipmentCommand toCommandFromResource(CreateEquipmentResource resource) {
        if (resource == null) return null;
        return new CreateEquipmentCommand(
                resource.code() != null ? new EquipmentCode(resource.code()) : null,
                resource.name(),
                resource.model(),
                resource.location() != null ? new Facility(resource.location()) : null,
                resource.calibrationStatus() != null ? CalibrationStatus.from(resource.calibrationStatus()) : CalibrationStatus.FIT,
                resource.calibrationDue(),
                resource.certificate(),
                resource.nextMaintenance(),
                resource.maintenanceOwner(),
                resource.workOrder(),
                resource.healthNote()
        );
    }
}
