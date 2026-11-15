package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.TraceabilityGapQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTraceabilityGapsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the traceability gaps.
 */
@Service
public class TraceabilityGapQueryServiceImpl implements TraceabilityGapQueryService {

    private final ITraceabilityGapRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the traceability gaps
     */
    public TraceabilityGapQueryServiceImpl(ITraceabilityGapRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TraceabilityGap> handle(GetAllTraceabilityGapsQuery query) {
        return query.batchCode() == null || query.batchCode().isBlank()
                ? repository.findAll()
                : repository.findByBatchCode(query.batchCode().trim());
    }

    @Override
    public Optional<TraceabilityGap> handle(GetTraceabilityGapByIdQuery query) {
        return repository.findById(query.traceabilityGapId());
    }
}
