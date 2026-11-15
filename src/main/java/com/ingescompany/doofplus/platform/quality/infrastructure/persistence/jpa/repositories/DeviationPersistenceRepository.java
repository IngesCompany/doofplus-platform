package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.DeviationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the deviations table.
 */
@Repository
public interface DeviationPersistenceRepository extends JpaRepository<DeviationPersistenceEntity, Long> {

    /**
     * Lists the rows with the given batch code.
     *
     * @param batchCode the batch code
     * @return the rows
     */
    List<DeviationPersistenceEntity> findByBatchCode(String batchCode);

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
