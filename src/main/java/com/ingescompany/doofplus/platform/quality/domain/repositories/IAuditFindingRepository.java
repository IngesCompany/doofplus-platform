package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the audit findings.
 */
public interface IAuditFindingRepository {

    /**
     * Saves a new or changed audit finding.
     *
     * @param auditFinding the audit finding
     * @return the saved audit finding, with its identifier
     */
    AuditFinding save(AuditFinding auditFinding);

    /**
     * Finds a audit finding by its identifier.
     *
     * @param id the identifier
     * @return the audit finding, if it exists
     */
    Optional<AuditFinding> findById(Long id);

    /**
     * Lists all the audit findings.
     *
     * @return the audit findings
     */
    List<AuditFinding> findAll();

    /**
     * Lists the audit findings with the given audit code.
     *
     * @param auditCode the audit code
     * @return the audit findings
     */
    List<AuditFinding> findByAuditCode(String auditCode);
}
