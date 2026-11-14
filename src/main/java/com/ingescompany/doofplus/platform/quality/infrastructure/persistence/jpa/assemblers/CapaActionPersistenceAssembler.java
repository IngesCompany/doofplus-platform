package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaActionPersistenceEntity;

public class CapaActionPersistenceAssembler {

    public static CapaAction toDomain(CapaActionPersistenceEntity entity) {
        return CapaAction.reconstitute(
                entity.getId(),
                entity.getCapaPlanId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAssignedTo(),
                entity.getDueDate(),
                entity.getStatus(),
                entity.getCompletedAt()
        );
    }

    public static CapaActionPersistenceEntity toEntity(CapaAction domain) {
        CapaActionPersistenceEntity entity = new CapaActionPersistenceEntity();

        entity.setId(domain.getId());
        entity.setCapaPlanId(domain.getCapaPlanId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setAssignedTo(domain.getAssignedTo());
        entity.setDueDate(domain.getDueDate());
        entity.setStatus(domain.getStatus());
        entity.setCompletedAt(domain.getCompletedAt());

        return entity;
    }
}