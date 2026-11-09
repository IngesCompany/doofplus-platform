package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;

/**
 * Assembler to translate between {@link Alert} domain aggregate and {@link AlertPersistenceEntity}.
 */
public final class AlertPersistenceAssembler {

    private AlertPersistenceAssembler() {
    }

    public static Alert toDomainFromPersistence(AlertPersistenceEntity entity) {
        if (entity == null) return null;
        return Alert.reconstitute(
                entity.getId(),
                entity.getCode(),
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

    public static AlertPersistenceEntity toPersistenceFromDomain(Alert domain) {
        if (domain == null) return null;
        var entity = new AlertPersistenceEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setSensorCode(domain.getSensorCode());
        entity.setEquipmentCode(domain.getEquipmentCode());
        entity.setSeverity(domain.getSeverity());
        entity.setStatus(domain.getStatus());
        entity.setTitle(domain.getTitle());
        entity.setDetail(domain.getDetail());
        entity.setBatchCode(domain.getBatchCode());
        entity.setAcknowledgedBy(domain.getAcknowledgedBy());
        entity.setAcknowledgedAt(domain.getAcknowledgedAt());
        entity.setResponseNote(domain.getResponseNote());
        entity.setLinkedRecord(domain.getLinkedRecord());
        return entity;
    }
}
