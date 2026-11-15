package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaPlansQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaPlanByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the capa plans.
 */
public interface CapaPlanQueryService {

    /**
     * Lists the capa plans, filtered when the query has a value.
     *
     * @param query the query
     * @return the capa plans
     */
    List<CapaPlan> handle(GetAllCapaPlansQuery query);

    /**
     * Finds a capa plan by its identifier.
     *
     * @param query the query
     * @return the capa plan, if it exists
     */
    Optional<CapaPlan> handle(GetCapaPlanByIdQuery query);
}
