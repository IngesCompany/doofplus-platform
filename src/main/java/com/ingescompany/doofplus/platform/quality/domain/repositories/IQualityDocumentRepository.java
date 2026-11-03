package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IQualityDocumentRepository {
    QualityDocument save(QualityDocument document);
    Optional<QualityDocument> findById(UUID id);
    Optional<QualityDocument> findByCode(String code);
    List<QualityDocument> findAll();
    void deleteById(UUID id);
}