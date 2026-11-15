package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditEventPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the audit_events table.
 */
@Repository
public interface AuditEventPersistenceRepository extends JpaRepository<AuditEventPersistenceEntity, Long> {

    /**
     * Lists the rows with the given record.
     *
     * @param record the record
     * @return the rows
     */
    List<AuditEventPersistenceEntity> findByRecord(String record);
}
