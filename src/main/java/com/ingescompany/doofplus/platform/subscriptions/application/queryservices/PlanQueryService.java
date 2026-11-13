package com.ingescompany.doofplus.platform.subscriptions.application.queryservices;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetAllPlansQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetPlanByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to plans.
 *
 * @since 1.0
 */
public interface PlanQueryService {
    /**
     * Retrieves all plans.
     *
     * @param query query without parameters
     * @return list of plans, possibly empty
     */
    List<Plan> handle(GetAllPlansQuery query);

    /**
     * Retrieves a plan by its identifier.
     *
     * @param query query containing the plan identifier
     * @return plan when found, otherwise empty
     */
    Optional<Plan> handle(GetPlanByIdQuery query);
}
