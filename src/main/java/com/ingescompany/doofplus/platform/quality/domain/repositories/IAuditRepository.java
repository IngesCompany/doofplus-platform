package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the audits.
 */
public interface IAuditRepository {

    /**
     * Saves a new or changed audit.
     *
     * @param audit the audit
     * @return the saved audit, with its identifier
     */
    Audit save(Audit audit);

    /**
     * Finds a audit by its identifier.
     *
     * @param id the identifier
     * @return the audit, if it exists
     */
    Optional<Audit> findById(Long id);

    /**
     * Lists all the audits.
     *
     * @return the audits
     */
    List<Audit> findAll();

    /**
     * Tells whether another audit already has the code.
     *
     * @param code the code
     * @param id   the identifier of the audit that keeps it, or null when it is new
     * @return true when the code is taken
     */
    boolean existsByCode(String code, Long id);
}
