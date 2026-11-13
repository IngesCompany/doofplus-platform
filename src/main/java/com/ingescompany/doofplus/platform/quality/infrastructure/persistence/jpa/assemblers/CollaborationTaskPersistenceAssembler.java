package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CollaborationTask;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CollaborationTaskPersistenceEntity;

public class CollaborationTaskPersistenceAssembler {

    public static CollaborationTask toDomain(CollaborationTaskPersistenceEntity entity) {
        return CollaborationTask.reconstitute(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAssignedTo(),
                entity.getCreatedBy(),
                entity.getRelatedDeviationId(),
                entity.getDueDate(),
                entity.getStatus(),
                entity.getCompletedAt()
        );
    }

    public static CollaborationTaskPersistenceEntity toEntity(CollaborationTask task) {
        CollaborationTaskPersistenceEntity entity = new CollaborationTaskPersistenceEntity();

        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setAssignedTo(task.getAssignedTo());
        entity.setCreatedBy(task.getCreatedBy());
        entity.setRelatedDeviationId(task.getRelatedDeviationId());
        entity.setDueDate(task.getDueDate());
        entity.setStatus(task.getStatus());
        entity.setCompletedAt(task.getCompletedAt());

        return entity;
    }
}