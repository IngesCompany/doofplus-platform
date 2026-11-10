package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapsByBatchIdQuery;

import java.util.List;

public interface TraceabilityGapQueryService {
    List<TraceabilityGap> handle(GetTraceabilityGapsByBatchIdQuery query);
}