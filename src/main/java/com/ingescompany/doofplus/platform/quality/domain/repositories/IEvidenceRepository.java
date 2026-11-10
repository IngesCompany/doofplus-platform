package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;

import java.util.List;
import java.util.Optional;

public interface IEvidenceRepository {

    Evidence save(Evidence evidence);

    Optional<Evidence> findById(Long id);

    List<Evidence> findAll();

    List<Evidence> findByDeviationId(Long deviationId);

    List<Evidence> findByDocumentId(Long documentId);
}