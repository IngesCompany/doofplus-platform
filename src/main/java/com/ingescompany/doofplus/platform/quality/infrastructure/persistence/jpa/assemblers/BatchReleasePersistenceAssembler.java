package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.BatchReleasePersistenceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class BatchReleasePersistenceAssembler {

    public static BatchRelease toDomain(BatchReleasePersistenceEntity entity) {
        return BatchRelease.reconstitute(
                entity.getId(),
                entity.getBatchId(),
                entity.getBatchCode(),
                entity.getRequestedBy(),
                entity.getStatus(),
                entity.getDecision(),
                entity.getSignatureReference(),
                entity.getSignedBy(),
                entity.getSignedAt(),
                toLocalDateTime(entity.getCreatedAt())
        );
    }

    public static BatchReleasePersistenceEntity toEntity(BatchRelease domain) {
        BatchReleasePersistenceEntity entity = new BatchReleasePersistenceEntity();

        entity.setId(domain.getId());
        entity.setBatchId(domain.getBatchId());
        entity.setBatchCode(domain.getBatchCode());
        entity.setRequestedBy(domain.getRequestedBy());
        entity.setStatus(domain.getStatus());
        entity.setDecision(domain.getDecision());
        entity.setSignatureReference(domain.getSignatureReference());
        entity.setSignedBy(domain.getSignedBy());
        entity.setSignedAt(domain.getSignedAt());

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