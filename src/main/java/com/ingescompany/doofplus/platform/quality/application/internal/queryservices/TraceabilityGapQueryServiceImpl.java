package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.TraceabilityGapQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTraceabilityGapsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraceabilityGapQueryServiceImpl implements TraceabilityGapQueryService {

    private final ITraceabilityGapRepository repository;

    public TraceabilityGapQueryServiceImpl(ITraceabilityGapRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TraceabilityGap> handle(GetAllTraceabilityGapsQuery query) {
        return repository.findAll();
    }

    @Override
    public List<TraceabilityGap> handle(GetTraceabilityGapsByBatchIdQuery query) {
        return repository.findByBatchId(query.batchId());
    }
}