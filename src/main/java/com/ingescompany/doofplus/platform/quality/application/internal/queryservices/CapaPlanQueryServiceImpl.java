package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.CapaPlanQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllCapaPlansQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetCapaPlanByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the capa plans.
 */
@Service
public class CapaPlanQueryServiceImpl implements CapaPlanQueryService {

    private final ICapaPlanRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the capa plans
     */
    public CapaPlanQueryServiceImpl(ICapaPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CapaPlan> handle(GetAllCapaPlansQuery query) {
        return query.sourceDeviation() == null || query.sourceDeviation().isBlank()
                ? repository.findAll()
                : repository.findBySourceDeviation(query.sourceDeviation().trim());
    }

    @Override
    public Optional<CapaPlan> handle(GetCapaPlanByIdQuery query) {
        return repository.findById(query.capaPlanId());
    }
}
