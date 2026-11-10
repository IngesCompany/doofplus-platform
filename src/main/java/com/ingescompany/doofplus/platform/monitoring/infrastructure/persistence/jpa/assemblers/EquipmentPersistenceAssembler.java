package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.EquipmentPersistenceEntity;

/**
 * Assembler to translate between {@link Equipment} domain aggregate and {@link EquipmentPersistenceEntity}.
 */
public final class EquipmentPersistenceAssembler {

    private EquipmentPersistenceAssembler() {
    }

    public static Equipment toDomainFromPersistence(EquipmentPersistenceEntity entity) {
        if (entity == null) return null;
        return Equipment.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getModel(),
                entity.getLocation(),
                entity.getCalibrationStatus(),
                entity.getCalibrationDue(),
                entity.getCertificate(),
                entity.getNextMaintenance(),
                entity.getMaintenanceOwner(),
                entity.getWorkOrder(),
                entity.getHealthNote()
        );
    }

    public static EquipmentPersistenceEntity toPersistenceFromDomain(Equipment domain) {
        if (domain == null) return null;
        var entity = new EquipmentPersistenceEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setModel(domain.getModel());
        entity.setLocation(domain.getLocation());
        entity.setCalibrationStatus(domain.getCalibrationStatus());
        entity.setCalibrationDue(domain.getCalibrationDue());
        entity.setCertificate(domain.getCertificate());
        entity.setNextMaintenance(domain.getNextMaintenance());
        entity.setMaintenanceOwner(domain.getMaintenanceOwner());
        entity.setWorkOrder(domain.getWorkOrder());
        entity.setHealthNote(domain.getHealthNote());
        return entity;
    }
}
