package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;

import java.util.List;
import java.util.Optional;

public interface IAuditFindingRepository {

    AuditFinding save(AuditFinding auditFinding);

    Optional<AuditFinding> findById(Long id);

    List<AuditFinding> findAll();

    List<AuditFinding> findByAuditId(Long auditId);
}