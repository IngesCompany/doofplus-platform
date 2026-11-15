package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllAnalyticalResultsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the analytical results.
 */
public interface AnalyticalResultQueryService {

    /**
     * Lists the analytical results, filtered when the query has a value.
     *
     * @param query the query
     * @return the analytical results
     */
    List<AnalyticalResult> handle(GetAllAnalyticalResultsQuery query);

    /**
     * Finds a analytical result by its identifier.
     *
     * @param query the query
     * @return the analytical result, if it exists
     */
    Optional<AnalyticalResult> handle(GetAnalyticalResultByIdQuery query);
}
