package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllTraceabilityGapsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetTraceabilityGapByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the traceability gaps.
 */
public interface TraceabilityGapQueryService {

    /**
     * Lists the traceability gaps, filtered when the query has a value.
     *
     * @param query the query
     * @return the traceability gaps
     */
    List<TraceabilityGap> handle(GetAllTraceabilityGapsQuery query);

    /**
     * Finds a traceability gap by its identifier.
     *
     * @param query the query
     * @return the traceability gap, if it exists
     */
    Optional<TraceabilityGap> handle(GetTraceabilityGapByIdQuery query);
}
