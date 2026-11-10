package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;

public class EvidencePersistenceAssembler {

    public static Evidence toDomain(EvidencePersistenceEntity entity) {
        return Evidence.reconstitute(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getType(),
                entity.getFileReference(),
                entity.getDeviationId(),
                entity.getDocumentId(),
                entity.getUploadedBy(),
                entity.getStatus(),
                entity.getUploadedAt(),
                entity.getVerifiedAt()
        );
    }

    public static EvidencePersistenceEntity toEntity(Evidence evidence) {
        EvidencePersistenceEntity entity = new EvidencePersistenceEntity();

        entity.setTitle(evidence.getTitle());
        entity.setDescription(evidence.getDescription());
        entity.setType(evidence.getType());
        entity.setFileReference(evidence.getFileReference());
        entity.setDeviationId(evidence.getDeviationId());
        entity.setDocumentId(evidence.getDocumentId());
        entity.setUploadedBy(evidence.getUploadedBy());
        entity.setStatus(evidence.getStatus());
        entity.setUploadedAt(evidence.getUploadedAt());
        entity.setVerifiedAt(evidence.getVerifiedAt());

        return entity;
    }
}