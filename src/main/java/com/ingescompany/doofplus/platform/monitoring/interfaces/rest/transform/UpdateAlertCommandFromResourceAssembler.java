package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.UpdateAlertResource;

/**
 * Assembler to translate an {@link UpdateAlertResource} and ID into an {@link UpdateAlertCommand}.
 */
public final class UpdateAlertCommandFromResourceAssembler {

    private UpdateAlertCommandFromResourceAssembler() {
    }

    public static UpdateAlertCommand toCommandFromResource(Long id, UpdateAlertResource resource) {
        if (resource == null) return null;
        return new UpdateAlertCommand(
                id,
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
