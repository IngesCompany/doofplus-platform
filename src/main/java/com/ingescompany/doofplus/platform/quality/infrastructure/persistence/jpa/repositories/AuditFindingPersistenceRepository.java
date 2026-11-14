package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditFindingPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditFindingPersistenceRepository extends JpaRepository<AuditFindingPersistenceEntity, Long> {

    List<AuditFindingPersistenceEntity> findByAuditId(Long auditId);
}