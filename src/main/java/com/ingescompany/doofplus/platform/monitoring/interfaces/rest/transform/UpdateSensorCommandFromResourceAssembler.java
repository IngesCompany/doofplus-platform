package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.UpdateSensorResource;

/**
 * Assembler to translate an {@link UpdateSensorResource} and ID into an {@link UpdateSensorCommand}.
 */
public final class UpdateSensorCommandFromResourceAssembler {

    private UpdateSensorCommandFromResourceAssembler() {
    }

    public static UpdateSensorCommand toCommandFromResource(Long id, UpdateSensorResource resource) {
        if (resource == null) return null;
        return new UpdateSensorCommand(
                id,
                resource.code() != null ? new SensorCode(resource.code()) : null,
                resource.deviceId() != null ? new DeviceId(resource.deviceId()) : null,
                resource.type(),
                resource.unit(),
                resource.equipmentCode(),
                resource.lowerLimit(),
                resource.upperLimit(),
                resource.status(),
                resource.assignedBatch()
        );
    }
}
