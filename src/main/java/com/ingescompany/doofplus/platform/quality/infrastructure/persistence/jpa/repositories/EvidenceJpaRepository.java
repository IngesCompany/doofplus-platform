package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EvidenceJpaRepository extends JpaRepository<Evidence, UUID> {
    List<Evidence> findByDeviationId(UUID deviationId);
    List<Evidence> findByAuditId(UUID auditId);
}