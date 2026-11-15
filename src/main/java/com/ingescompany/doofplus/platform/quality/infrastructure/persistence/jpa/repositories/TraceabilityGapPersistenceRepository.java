package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.TraceabilityGapPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the traceability_gaps table.
 */
@Repository
public interface TraceabilityGapPersistenceRepository extends JpaRepository<TraceabilityGapPersistenceEntity, Long> {

    /**
     * Lists the rows with the given batch code.
     *
     * @param batchCode the batch code
     * @return the rows
     */
    List<TraceabilityGapPersistenceEntity> findByBatchCode(String batchCode);
}
