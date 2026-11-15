package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTraceabilityGapsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapsByBatchIdQuery;

import java.util.List;

public interface TraceabilityGapQueryService {

    List<TraceabilityGap> handle(GetAllTraceabilityGapsQuery query);

    List<TraceabilityGap> handle(GetTraceabilityGapsByBatchIdQuery query);

    /**
     * Gets a TraceabilityGap by its identifier.
     *
     * @param query the query
     * @return the TraceabilityGap, or empty when it does not exist
     */
    Optional<TraceabilityGap> handle(GetTraceabilityGapByIdQuery query);
}
