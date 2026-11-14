package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateSensorResource;

/**
 * Assembler to translate a {@link CreateSensorResource} into a {@link CreateSensorCommand}.
 */
public final class CreateSensorCommandFromResourceAssembler {

    private CreateSensorCommandFromResourceAssembler() {
    }

    public static CreateSensorCommand toCommandFromResource(CreateSensorResource resource) {
        if (resource == null) return null;
        return new CreateSensorCommand(
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
