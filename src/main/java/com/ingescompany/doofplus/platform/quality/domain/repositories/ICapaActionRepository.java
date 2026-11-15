package com.ingescompany.doofplus.platform.quality.domain.repositories;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository of the capa actions.
 */
public interface ICapaActionRepository {

    /**
     * Saves a new or changed capa action.
     *
     * @param capaAction the capa action
     * @return the saved capa action, with its identifier
     */
    CapaAction save(CapaAction capaAction);

    /**
     * Finds a capa action by its identifier.
     *
     * @param id the identifier
     * @return the capa action, if it exists
     */
    Optional<CapaAction> findById(Long id);

    /**
     * Lists all the capa actions.
     *
     * @return the capa actions
     */
    List<CapaAction> findAll();

    /**
     * Lists the capa actions with the given capa code.
     *
     * @param capaCode the capa code
     * @return the capa actions
     */
    List<CapaAction> findByCapaCode(String capaCode);
}
