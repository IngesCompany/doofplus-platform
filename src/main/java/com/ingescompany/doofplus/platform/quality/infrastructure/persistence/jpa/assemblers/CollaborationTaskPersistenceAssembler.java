package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CollaborationTaskPersistenceEntity;

/**
 * Converts the collaboration tasks between the domain and the persistence entity.
 */
public final class CollaborationTaskPersistenceAssembler {

    private CollaborationTaskPersistenceAssembler() {
    }

    /**
     * Copies the data of the collaboration task to a row.
     *
     * @param collaborationTask    the collaboration task
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static CollaborationTaskPersistenceEntity toEntity(CollaborationTask collaborationTask, CollaborationTaskPersistenceEntity entity) {
        entity.setTitle(collaborationTask.getTitle());
        entity.setRecordCode(collaborationTask.getRecordCode());
        entity.setDetail(collaborationTask.getDetail());
        entity.setOwner(collaborationTask.getOwner());
        entity.setDueAt(collaborationTask.getDueAt());
        entity.setStatus(collaborationTask.getStatus());
        return entity;
    }

    /**
     * Rebuilds the collaboration task of a row.
     *
     * @param entity the row
     * @return the collaboration task
     */
    public static CollaborationTask toDomain(CollaborationTaskPersistenceEntity entity) {
        return CollaborationTask.reconstitute(entity.getId(), entity.getTitle(), entity.getRecordCode(), entity.getDetail(), entity.getOwner(), entity.getDueAt(), entity.getStatus());
    }
}
