package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QualityDocumentJpaRepository extends JpaRepository<QualityDocument, UUID> {
    Optional<QualityDocument> findByCode(String code);
}