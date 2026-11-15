package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the analytical results.
 */
public interface IAnalyticalResultRepository {

    /**
     * Saves a new or changed analytical result.
     *
     * @param analyticalResult the analytical result
     * @return the saved analytical result, with its identifier
     */
    AnalyticalResult save(AnalyticalResult analyticalResult);

    /**
     * Finds a analytical result by its identifier.
     *
     * @param id the identifier
     * @return the analytical result, if it exists
     */
    Optional<AnalyticalResult> findById(Long id);

    /**
     * Lists all the analytical results.
     *
     * @return the analytical results
     */
    List<AnalyticalResult> findAll();

    /**
     * Lists the analytical results with the given batch code.
     *
     * @param batchCode the batch code
     * @return the analytical results
     */
    List<AnalyticalResult> findByBatchCode(String batchCode);
}
