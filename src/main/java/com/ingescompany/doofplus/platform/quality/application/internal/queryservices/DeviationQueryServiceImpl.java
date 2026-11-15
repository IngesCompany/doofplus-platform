package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.DeviationQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllDeviationsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the deviations.
 */
@Service
public class DeviationQueryServiceImpl implements DeviationQueryService {

    private final IDeviationRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the deviations
     */
    public DeviationQueryServiceImpl(IDeviationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Deviation> handle(GetAllDeviationsQuery query) {
        return query.batchCode() == null || query.batchCode().isBlank()
                ? repository.findAll()
                : repository.findByBatchCode(query.batchCode().trim());
    }

    @Override
    public Optional<Deviation> handle(GetDeviationByIdQuery query) {
        return repository.findById(query.deviationId());
    }
}
