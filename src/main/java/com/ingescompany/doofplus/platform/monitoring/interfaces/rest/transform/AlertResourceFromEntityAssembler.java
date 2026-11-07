package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.AlertResource;

/**
 * Assembler to translate an {@link Alert} domain aggregate into an {@link AlertResource}.
 */
public final class AlertResourceFromEntityAssembler {

    private AlertResourceFromEntityAssembler() {
    }

    public static AlertResource toResourceFromEntity(Alert entity) {
        if (entity == null) return null;
        return new AlertResource(
                entity.getId(),
                entity.getCode() != null ? entity.getCode().value() : null,
                entity.getSensorCode(),
                entity.getEquipmentCode(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getTitle(),
                entity.getDetail(),
                entity.getBatchCode(),
                entity.getAcknowledgedBy(),
                entity.getAcknowledgedAt(),
                entity.getResponseNote(),
                entity.getLinkedRecord()
        );
    }
}
