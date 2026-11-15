package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditFindingPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the audit_findings table.
 */
@Repository
public interface AuditFindingPersistenceRepository extends JpaRepository<AuditFindingPersistenceEntity, Long> {

    /**
     * Lists the rows with the given audit code.
     *
     * @param auditCode the audit code
     * @return the rows
     */
    List<AuditFindingPersistenceEntity> findByAuditCode(String auditCode);
}
