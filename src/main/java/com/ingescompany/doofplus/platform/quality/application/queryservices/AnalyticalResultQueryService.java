package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultsByBatchIdQuery;

import java.util.List;

public interface AnalyticalResultQueryService {
    List<AnalyticalResult> handle(GetAnalyticalResultsByBatchIdQuery query);

    /**
     * Gets a AnalyticalResult by its identifier.
     *
     * @param query the query
     * @return the AnalyticalResult, or empty when it does not exist
     */
    Optional<AnalyticalResult> handle(GetAnalyticalResultByIdQuery query);
}
