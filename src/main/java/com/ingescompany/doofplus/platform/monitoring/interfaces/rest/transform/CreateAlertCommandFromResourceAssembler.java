package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.CreateAlertResource;

/**
 * Assembler to translate a {@link CreateAlertResource} into a {@link CreateAlertCommand}.
 */
public final class CreateAlertCommandFromResourceAssembler {

    private CreateAlertCommandFromResourceAssembler() {
    }

    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource) {
        if (resource == null) return null;
        return new CreateAlertCommand(
                resource.code() != null ? new AlertCode(resource.code()) : null,
                resource.sensorCode(),
                resource.equipmentCode(),
                resource.severity(),
                resource.status(),
                resource.title(),
                resource.detail(),
                resource.batchCode(),
                resource.acknowledgedBy(),
                resource.acknowledgedAt(),
                resource.responseNote(),
                resource.linkedRecord()
        );
    }
}
