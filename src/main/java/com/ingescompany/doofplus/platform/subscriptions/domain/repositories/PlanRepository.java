package com.ingescompany.doofplus.platform.subscriptions.domain.repositories;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;

import java.util.List;
import java.util.Optional;

/**
 * Repository contract for plans, implemented in the infrastructure layer.
 */
public interface PlanRepository {
    Plan save(Plan plan);
    Optional<Plan> findById(Long id);
    List<Plan> findAll();
    boolean existsById(Long id);
    boolean existsByPlanKey(PlanKey planKey);
}
