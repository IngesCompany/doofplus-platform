package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.DeviationPersistenceEntity;

/**
 * Converts the deviations between the domain and the persistence entity.
 */
public final class DeviationPersistenceAssembler {

    private DeviationPersistenceAssembler() {
    }

    /**
     * Copies the data of the deviation to a row.
     *
     * @param deviation    the deviation
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static DeviationPersistenceEntity toEntity(Deviation deviation, DeviationPersistenceEntity entity) {
        entity.setCode(deviation.getCode());
        entity.setTitle(deviation.getTitle());
        entity.setBatchCode(deviation.getBatchCode());
        entity.setOrderCode(deviation.getOrderCode());
        entity.setSummary(deviation.getSummary());
        entity.setClassification(deviation.getClassification());
        entity.setContainment(deviation.getContainment());
        entity.setSeverity(deviation.getSeverity());
        entity.setLikelihood(deviation.getLikelihood());
        entity.setDetectability(deviation.getDetectability());
        entity.setStatus(deviation.getStatus());
        entity.setRootCause(deviation.getRootCause());
        entity.setCapaCode(deviation.getCapaCode());
        entity.setReportedBy(deviation.getReportedBy());
        entity.setReportedAt(deviation.getReportedAt());
        entity.setOwner(deviation.getOwner());
        entity.setDueAt(deviation.getDueAt());
        return entity;
    }

    /**
     * Rebuilds the deviation of a row.
     *
     * @param entity the row
     * @return the deviation
     */
    public static Deviation toDomain(DeviationPersistenceEntity entity) {
        return Deviation.reconstitute(entity.getId(), entity.getCode(), entity.getTitle(), entity.getBatchCode(), entity.getOrderCode(), entity.getSummary(), entity.getClassification(), entity.getContainment(), entity.getSeverity(), entity.getLikelihood(), entity.getDetectability(), entity.getStatus(), entity.getRootCause(), entity.getCapaCode(), entity.getReportedBy(), entity.getReportedAt(), entity.getOwner(), entity.getDueAt());
    }
}
