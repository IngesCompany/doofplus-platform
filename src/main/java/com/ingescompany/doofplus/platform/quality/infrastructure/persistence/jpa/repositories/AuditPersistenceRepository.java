package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AuditPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the audits table.
 */
@Repository
public interface AuditPersistenceRepository extends JpaRepository<AuditPersistenceEntity, Long> {

    /**
     * Tells whether a row has the code.
     *
     * @param code the code
     * @return true when it exists
     */
    boolean existsByCode(String code);

    /**
     * Tells whether a row other than the given one has the code.
     *
     * @param code the code
     * @param id   the identifier of the row to leave out
     * @return true when it exists
     */
    boolean existsByCodeAndIdNot(String code, Long id);
}
