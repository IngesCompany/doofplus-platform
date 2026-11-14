package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.EquipmentResource;

/**
 * Assembler to translate an {@link Equipment} domain aggregate into an {@link EquipmentResource}.
 */
public final class EquipmentResourceFromEntityAssembler {

    private EquipmentResourceFromEntityAssembler() {
    }

    public static EquipmentResource toResourceFromEntity(Equipment entity) {
        if (entity == null) return null;
        return new EquipmentResource(
                entity.getId(),
                entity.getCode() != null ? entity.getCode().value() : null,
                entity.getName(),
                entity.getModel(),
                entity.getLocation() != null ? entity.getLocation().value() : null,
                entity.getCalibrationStatus() != null ? entity.getCalibrationStatus().value() : null,
                entity.getCalibrationDue(),
                entity.getCertificate(),
                entity.getNextMaintenance(),
                entity.getMaintenanceOwner(),
                entity.getWorkOrder(),
                entity.getHealthNote()
        );
    }
}
