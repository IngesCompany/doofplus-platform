package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TraceabilityGapPersistenceEntity;

/**
 * Converts the traceability gaps between the domain and the persistence entity.
 */
public final class TraceabilityGapPersistenceAssembler {

    private TraceabilityGapPersistenceAssembler() {
    }

    /**
     * Copies the data of the traceability gap to a row.
     *
     * @param traceabilityGap    the traceability gap
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static TraceabilityGapPersistenceEntity toEntity(TraceabilityGap traceabilityGap, TraceabilityGapPersistenceEntity entity) {
        entity.setBatchCode(traceabilityGap.getBatchCode());
        entity.setProduct(traceabilityGap.getProduct());
        entity.setMissingRecord(traceabilityGap.getMissingRecord());
        entity.setDetail(traceabilityGap.getDetail());
        entity.setStatus(traceabilityGap.getStatus());
        return entity;
    }

    /**
     * Rebuilds the traceability gap of a row.
     *
     * @param entity the row
     * @return the traceability gap
     */
    public static TraceabilityGap toDomain(TraceabilityGapPersistenceEntity entity) {
        return TraceabilityGap.reconstitute(entity.getId(), entity.getBatchCode(), entity.getProduct(), entity.getMissingRecord(), entity.getDetail(), entity.getStatus());
    }
}
