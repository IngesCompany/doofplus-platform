package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.time.YearMonth;
import java.util.Map;

public interface DeviationTrendQueryService {
    Map<YearMonth, Long> getTrends();
}