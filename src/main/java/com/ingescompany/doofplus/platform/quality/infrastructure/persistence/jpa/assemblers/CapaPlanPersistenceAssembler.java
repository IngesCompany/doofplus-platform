package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaPlanPersistenceEntity;

/**
 * Converts the capa plans between the domain and the persistence entity.
 */
public final class CapaPlanPersistenceAssembler {

    private CapaPlanPersistenceAssembler() {
    }

    /**
     * Copies the data of the capa plan to a row.
     *
     * @param capaPlan    the capa plan
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static CapaPlanPersistenceEntity toEntity(CapaPlan capaPlan, CapaPlanPersistenceEntity entity) {
        entity.setCode(capaPlan.getCode());
        entity.setTitle(capaPlan.getTitle());
        entity.setSourceDeviation(capaPlan.getSourceDeviation());
        entity.setOwner(capaPlan.getOwner());
        entity.setReviewer(capaPlan.getReviewer());
        entity.setRootCause(capaPlan.getRootCause());
        entity.setAcceptanceCriterion(capaPlan.getAcceptanceCriterion());
        entity.setEffectivenessCheckAt(capaPlan.getEffectivenessCheckAt());
        entity.setStatus(capaPlan.getStatus());
        return entity;
    }

    /**
     * Rebuilds the capa plan of a row.
     *
     * @param entity the row
     * @return the capa plan
     */
    public static CapaPlan toDomain(CapaPlanPersistenceEntity entity) {
        return CapaPlan.reconstitute(entity.getId(), entity.getCode(), entity.getTitle(), entity.getSourceDeviation(), entity.getOwner(), entity.getReviewer(), entity.getRootCause(), entity.getAcceptanceCriterion(), entity.getEffectivenessCheckAt(), entity.getStatus());
    }
}
