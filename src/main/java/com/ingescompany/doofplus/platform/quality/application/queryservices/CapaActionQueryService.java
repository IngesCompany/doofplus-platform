package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaActionsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaActionByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the capa actions.
 */
public interface CapaActionQueryService {

    /**
     * Lists the capa actions, filtered when the query has a value.
     *
     * @param query the query
     * @return the capa actions
     */
    List<CapaAction> handle(GetAllCapaActionsQuery query);

    /**
     * Finds a capa action by its identifier.
     *
     * @param query the query
     * @return the capa action, if it exists
     */
    Optional<CapaAction> handle(GetCapaActionByIdQuery query);
}
