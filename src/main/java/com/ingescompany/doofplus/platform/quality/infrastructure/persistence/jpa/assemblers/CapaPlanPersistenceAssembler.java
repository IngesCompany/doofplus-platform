package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaPlanPersistenceEntity;

public class CapaPlanPersistenceAssembler {

    public static CapaPlan toDomain(CapaPlanPersistenceEntity entity) {
        return CapaPlan.reconstitute(
                entity.getId(),
                entity.getDeviationId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getDueDate(),
                entity.getResponsible(),
                entity.getCreatedAt(),
                entity.getCompletedAt()
        );
    }

    public static CapaPlanPersistenceEntity toEntity(CapaPlan domain) {
        CapaPlanPersistenceEntity entity = new CapaPlanPersistenceEntity();

        entity.setId(domain.getId());
        entity.setDeviationId(domain.getDeviationId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setDueDate(domain.getDueDate());
        entity.setResponsible(domain.getResponsible());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setCompletedAt(domain.getCompletedAt());

        return entity;
    }
}