package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.AnalyticalResultPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the analytical_results table.
 */
@Repository
public interface AnalyticalResultPersistenceRepository extends JpaRepository<AnalyticalResultPersistenceEntity, Long> {

    /**
     * Lists the rows with the given batch code.
     *
     * @param batchCode the batch code
     * @return the rows
     */
    List<AnalyticalResultPersistenceEntity> findByBatchCode(String batchCode);
}
