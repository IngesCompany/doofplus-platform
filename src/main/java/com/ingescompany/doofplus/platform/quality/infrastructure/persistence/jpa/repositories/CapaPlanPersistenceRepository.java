package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.CapaPlanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository of the capa_plans table.
 */
@Repository
public interface CapaPlanPersistenceRepository extends JpaRepository<CapaPlanPersistenceEntity, Long> {

    /**
     * Lists the rows with the given source deviation.
     *
     * @param sourceDeviation the source deviation
     * @return the rows
     */
    List<CapaPlanPersistenceEntity> findBySourceDeviation(String sourceDeviation);

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
