package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetDeviationTrendsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationTrend;

import java.util.List;

/**
 * Query service of the deviation trend.
 */
public interface DeviationTrendQueryService {

    /**
     * Counts the deviations by month of their report and root cause, oldest month first.
     *
     * @param query the query
     * @return the trend
     */
    List<DeviationTrend> handle(GetDeviationTrendsQuery query);
}
