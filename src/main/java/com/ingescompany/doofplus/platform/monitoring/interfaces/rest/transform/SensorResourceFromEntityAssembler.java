package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.SensorResource;

/**
 * Assembler to translate a {@link Sensor} domain aggregate into a {@link SensorResource}.
 */
public final class SensorResourceFromEntityAssembler {

    private SensorResourceFromEntityAssembler() {
    }

    public static SensorResource toResourceFromEntity(Sensor entity) {
        if (entity == null) return null;
        return new SensorResource(
                entity.getId(),
                entity.getCode() != null ? entity.getCode().value() : null,
                entity.getDeviceId() != null ? entity.getDeviceId().value() : null,
                entity.getType(),
                entity.getUnit(),
                entity.getEquipmentCode(),
                entity.getLowerLimit(),
                entity.getUpperLimit(),
                entity.getStatus(),
                entity.getAssignedBatch()
        );
    }
}
