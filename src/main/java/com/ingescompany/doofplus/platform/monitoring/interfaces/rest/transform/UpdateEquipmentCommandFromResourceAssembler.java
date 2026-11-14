package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.UpdateEquipmentResource;

/**
 * Assembler to translate an {@link UpdateEquipmentResource} and ID into an {@link UpdateEquipmentCommand}.
 */
public final class UpdateEquipmentCommandFromResourceAssembler {

    private UpdateEquipmentCommandFromResourceAssembler() {
    }

    public static UpdateEquipmentCommand toCommandFromResource(Long id, UpdateEquipmentResource resource) {
        if (resource == null) return null;
        return new UpdateEquipmentCommand(
                id,
                resource.code() != null ? new EquipmentCode(resource.code()) : null,
                resource.name(),
                resource.model(),
                resource.location() != null ? new Facility(resource.location()) : null,
                resource.calibrationStatus() != null ? CalibrationStatus.from(resource.calibrationStatus()) : null,
                resource.calibrationDue(),
                resource.certificate(),
                resource.nextMaintenance(),
                resource.maintenanceOwner(),
                resource.workOrder(),
                resource.healthNote()
        );
    }
}
