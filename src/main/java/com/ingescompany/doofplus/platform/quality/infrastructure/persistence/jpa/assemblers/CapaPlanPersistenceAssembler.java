package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaPlanPersistenceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
                toLocalDateTime(entity.getCreatedAt()),
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
        entity.setCompletedAt(domain.getCompletedAt());

        return entity;
    }

    /**
     * The creation date comes from the audit column of the base persistence entity.
     */
    private static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}