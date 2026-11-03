package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAuditFindingRepository {
    AuditFinding save(AuditFinding finding);
    Optional<AuditFinding> findById(UUID id);
    List<AuditFinding> findAll();
    List<AuditFinding> findByAuditId(UUID auditId);
    void deleteById(UUID id);
}