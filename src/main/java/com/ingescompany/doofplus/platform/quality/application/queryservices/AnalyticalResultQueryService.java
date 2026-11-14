package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAnalyticalResultsByBatchIdQuery;

import java.util.List;

public interface AnalyticalResultQueryService {
    List<AnalyticalResult> handle(GetAnalyticalResultsByBatchIdQuery query);
}