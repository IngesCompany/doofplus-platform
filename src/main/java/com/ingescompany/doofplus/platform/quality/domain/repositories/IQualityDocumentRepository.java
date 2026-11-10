package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;

import java.util.List;
import java.util.Optional;

public interface IQualityDocumentRepository {

    QualityDocument save(QualityDocument document);

    Optional<QualityDocument> findById(Long id);

    Optional<QualityDocument> findByCode(String code);

    List<QualityDocument> findAll();

    boolean existsByCode(String code);
}