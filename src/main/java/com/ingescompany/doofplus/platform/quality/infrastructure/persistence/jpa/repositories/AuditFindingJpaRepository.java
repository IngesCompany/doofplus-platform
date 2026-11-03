package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditFindingJpaRepository extends JpaRepository<AuditFinding, UUID> {
    List<AuditFinding> findByAuditId(UUID auditId);
}