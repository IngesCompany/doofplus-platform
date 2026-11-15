package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;

/**
 * Converts the evidence between the domain and the persistence entity.
 */
public final class EvidencePersistenceAssembler {

    private EvidencePersistenceAssembler() {
    }

    /**
     * Copies the data of the evidence to a row.
     *
     * @param evidence    the evidence
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static EvidencePersistenceEntity toEntity(Evidence evidence, EvidencePersistenceEntity entity) {
        entity.setRecordCode(evidence.getRecordCode());
        entity.setFileName(evidence.getFileName());
        entity.setDetail(evidence.getDetail());
        entity.setStatus(evidence.getStatus());
        return entity;
    }

    /**
     * Rebuilds the evidence of a row.
     *
     * @param entity the row
     * @return the evidence
     */
    public static Evidence toDomain(EvidencePersistenceEntity entity) {
        return Evidence.reconstitute(entity.getId(), entity.getRecordCode(), entity.getFileName(), entity.getDetail(), entity.getStatus());
    }
}
