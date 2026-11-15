package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaActionPersistenceEntity;

/**
 * Converts the capa actions between the domain and the persistence entity.
 */
public final class CapaActionPersistenceAssembler {

    private CapaActionPersistenceAssembler() {
    }

    /**
     * Copies the data of the capa action to a row.
     *
     * @param capaAction    the capa action
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static CapaActionPersistenceEntity toEntity(CapaAction capaAction, CapaActionPersistenceEntity entity) {
        entity.setCapaCode(capaAction.getCapaCode());
        entity.setCode(capaAction.getCode());
        entity.setType(capaAction.getType());
        entity.setTitle(capaAction.getTitle());
        entity.setOwner(capaAction.getOwner());
        entity.setDueAt(capaAction.getDueAt());
        entity.setStatus(capaAction.getStatus());
        entity.setEvidence(capaAction.getEvidence());
        entity.setEvidenceAttached(capaAction.getEvidenceAttached());
        return entity;
    }

    /**
     * Rebuilds the capa action of a row.
     *
     * @param entity the row
     * @return the capa action
     */
    public static CapaAction toDomain(CapaActionPersistenceEntity entity) {
        return CapaAction.reconstitute(entity.getId(), entity.getCapaCode(), entity.getCode(), entity.getType(), entity.getTitle(), entity.getOwner(), entity.getDueAt(), entity.getStatus(), entity.getEvidence(), entity.getEvidenceAttached());
    }
}
