package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the deviations.
 */
public interface IDeviationRepository {

    /**
     * Saves a new or changed deviation.
     *
     * @param deviation the deviation
     * @return the saved deviation, with its identifier
     */
    Deviation save(Deviation deviation);

    /**
     * Finds a deviation by its identifier.
     *
     * @param id the identifier
     * @return the deviation, if it exists
     */
    Optional<Deviation> findById(Long id);

    /**
     * Lists all the deviations.
     *
     * @return the deviations
     */
    List<Deviation> findAll();

    /**
     * Lists the deviations with the given batch code.
     *
     * @param batchCode the batch code
     * @return the deviations
     */
    List<Deviation> findByBatchCode(String batchCode);

    /**
     * Tells whether another deviation already has the code.
     *
     * @param code the code
     * @param id   the identifier of the deviation that keeps it, or null when it is new
     * @return true when the code is taken
     */
    boolean existsByCode(String code, Long id);
}
