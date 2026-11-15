package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the audit events.
 */
public interface IAuditEventRepository {

    /**
     * Saves a new or changed audit event.
     *
     * @param auditEvent the audit event
     * @return the saved audit event, with its identifier
     */
    AuditEvent save(AuditEvent auditEvent);

    /**
     * Finds a audit event by its identifier.
     *
     * @param id the identifier
     * @return the audit event, if it exists
     */
    Optional<AuditEvent> findById(Long id);

    /**
     * Lists all the audit events.
     *
     * @return the audit events
     */
    List<AuditEvent> findAll();

    /**
     * Lists the audit events with the given record.
     *
     * @param record the record
     * @return the audit events
     */
    List<AuditEvent> findByRecord(String record);
}
