package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.QualityDocumentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualityDocumentPersistenceRepository extends JpaRepository<QualityDocumentPersistenceEntity, Long> {

    Optional<QualityDocumentPersistenceEntity> findByCode(String code);

    boolean existsByCode(String code);
}