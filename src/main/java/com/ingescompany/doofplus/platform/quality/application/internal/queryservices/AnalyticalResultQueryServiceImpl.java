package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.AnalyticalResultQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAnalyticalResultsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAnalyticalResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the analytical results.
 */
@Service
public class AnalyticalResultQueryServiceImpl implements AnalyticalResultQueryService {

    private final IAnalyticalResultRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the analytical results
     */
    public AnalyticalResultQueryServiceImpl(IAnalyticalResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AnalyticalResult> handle(GetAllAnalyticalResultsQuery query) {
        return query.batchCode() == null || query.batchCode().isBlank()
                ? repository.findAll()
                : repository.findByBatchCode(query.batchCode().trim());
    }

    @Override
    public Optional<AnalyticalResult> handle(GetAnalyticalResultByIdQuery query) {
        return repository.findById(query.analyticalResultId());
    }
}
