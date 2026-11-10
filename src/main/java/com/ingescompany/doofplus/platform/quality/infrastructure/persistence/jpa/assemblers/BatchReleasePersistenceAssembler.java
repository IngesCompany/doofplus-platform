package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.BatchReleasePersistenceEntity;

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
                entity.getCreatedAt()
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
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;
    }
}