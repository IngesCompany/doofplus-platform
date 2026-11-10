package com.ingescompany.doofplus.platform.subscriptions.application.internal.queryservices;

import com.ingescompany.doofplus.platform.subscriptions.application.queryservices.PlanQueryService;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetAllPlansQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetPlanByIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.repositories.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service for plan query operations.
 *
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class PlanQueryServiceImpl implements PlanQueryService {
    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        var results = planRepository.findAll();
        log.debug("Found {} plan(s)", results.size());
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Plan> handle(GetPlanByIdQuery query) {
        log.debug("Querying plan by id={}", query.id());
        return planRepository.findById(query.id());
    }
}
