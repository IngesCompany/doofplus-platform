package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidencePersistenceRepository extends JpaRepository<EvidencePersistenceEntity, Long> {

    List<EvidencePersistenceEntity> findByDeviationId(Long deviationId);

    List<EvidencePersistenceEntity> findByDocumentId(Long documentId);
}