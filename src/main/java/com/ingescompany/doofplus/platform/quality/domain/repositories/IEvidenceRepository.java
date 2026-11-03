package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEvidenceRepository {
    Evidence save(Evidence evidence);
    Optional<Evidence> findById(UUID id);
    List<Evidence> findAll();
    List<Evidence> findByDeviationId(UUID deviationId);
    List<Evidence> findByAuditId(UUID auditId);
    void deleteById(UUID id);
}