package com.ingescompany.doofplus.platform.subscriptions.domain.model.queries;

/**
 * Query for retrieving a plan by its identifier.
 *
 * @param id plan identifier
 */
public record GetPlanByIdQuery(Long id) {
    public GetPlanByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("plan.error.id.invalid");
        }
    }
}
