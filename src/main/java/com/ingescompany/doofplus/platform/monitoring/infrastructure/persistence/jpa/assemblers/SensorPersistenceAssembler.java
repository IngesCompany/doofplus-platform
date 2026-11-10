package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.SensorPersistenceEntity;

/**
 * Assembler to translate between {@link Sensor} domain aggregate and {@link SensorPersistenceEntity}.
 */
public final class SensorPersistenceAssembler {

    private SensorPersistenceAssembler() {
    }

    public static Sensor toDomainFromPersistence(SensorPersistenceEntity entity) {
        if (entity == null) return null;
        return Sensor.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getDeviceId(),
                entity.getType(),
                entity.getUnit(),
                entity.getEquipmentCode(),
                entity.getLowerLimit(),
                entity.getUpperLimit(),
                entity.getStatus(),
                entity.getAssignedBatch()
        );
    }

    public static SensorPersistenceEntity toPersistenceFromDomain(Sensor domain) {
        if (domain == null) return null;
        var entity = new SensorPersistenceEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setDeviceId(domain.getDeviceId());
        entity.setType(domain.getType());
        entity.setUnit(domain.getUnit());
        entity.setEquipmentCode(domain.getEquipmentCode());
        entity.setLowerLimit(domain.getLowerLimit());
        entity.setUpperLimit(domain.getUpperLimit());
        entity.setStatus(domain.getStatus());
        entity.setAssignedBatch(domain.getAssignedBatch());
        return entity;
    }
}
