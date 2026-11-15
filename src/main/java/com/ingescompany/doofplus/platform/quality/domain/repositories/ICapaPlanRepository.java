package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the capa plans.
 */
public interface ICapaPlanRepository {

    /**
     * Saves a new or changed capa plan.
     *
     * @param capaPlan the capa plan
     * @return the saved capa plan, with its identifier
     */
    CapaPlan save(CapaPlan capaPlan);

    /**
     * Finds a capa plan by its identifier.
     *
     * @param id the identifier
     * @return the capa plan, if it exists
     */
    Optional<CapaPlan> findById(Long id);

    /**
     * Lists all the capa plans.
     *
     * @return the capa plans
     */
    List<CapaPlan> findAll();

    /**
     * Lists the capa plans with the given source deviation.
     *
     * @param sourceDeviation the source deviation
     * @return the capa plans
     */
    List<CapaPlan> findBySourceDeviation(String sourceDeviation);

    /**
     * Tells whether another capa plan already has the code.
     *
     * @param code the code
     * @param id   the identifier of the capa plan that keeps it, or null when it is new
     * @return true when the code is taken
     */
    boolean existsByCode(String code, Long id);
}
