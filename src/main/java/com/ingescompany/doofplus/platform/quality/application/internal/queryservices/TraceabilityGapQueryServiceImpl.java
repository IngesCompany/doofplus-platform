package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.TraceabilityGapQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapsByBatchIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraceabilityGapQueryServiceImpl implements TraceabilityGapQueryService {

    private final ITraceabilityGapRepository traceabilityGapRepository;

    public TraceabilityGapQueryServiceImpl(ITraceabilityGapRepository traceabilityGapRepository) {
        this.traceabilityGapRepository = traceabilityGapRepository;
    }

    @Override
    public List<TraceabilityGap> handle(GetTraceabilityGapsByBatchIdQuery query) {
        return traceabilityGapRepository.findByBatchId(query.batchId());
    }
}