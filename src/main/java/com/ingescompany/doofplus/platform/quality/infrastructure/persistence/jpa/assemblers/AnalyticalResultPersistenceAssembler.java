package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AnalyticalResultPersistenceEntity;

/**
 * Converts the analytical results between the domain and the persistence entity.
 */
public final class AnalyticalResultPersistenceAssembler {

    private AnalyticalResultPersistenceAssembler() {
    }

    /**
     * Copies the data of the analytical result to a row.
     *
     * @param analyticalResult    the analytical result
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static AnalyticalResultPersistenceEntity toEntity(AnalyticalResult analyticalResult, AnalyticalResultPersistenceEntity entity) {
        entity.setBatchCode(analyticalResult.getBatchCode());
        entity.setSampleCode(analyticalResult.getSampleCode());
        entity.setReportCode(analyticalResult.getReportCode());
        entity.setTest(analyticalResult.getTest());
        entity.setProtocol(analyticalResult.getProtocol());
        entity.setResult(analyticalResult.getResult());
        entity.setSpecification(analyticalResult.getSpecification());
        entity.setAnalyst(analyticalResult.getAnalyst());
        entity.setStatus(analyticalResult.getStatus());
        entity.setApproval(analyticalResult.getApproval());
        return entity;
    }

    /**
     * Rebuilds the analytical result of a row.
     *
     * @param entity the row
     * @return the analytical result
     */
    public static AnalyticalResult toDomain(AnalyticalResultPersistenceEntity entity) {
        return AnalyticalResult.reconstitute(entity.getId(), entity.getBatchCode(), entity.getSampleCode(), entity.getReportCode(), entity.getTest(), entity.getProtocol(), entity.getResult(), entity.getSpecification(), entity.getAnalyst(), entity.getStatus(), entity.getApproval());
    }
}
