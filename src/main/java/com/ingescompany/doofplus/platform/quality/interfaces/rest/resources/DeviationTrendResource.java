package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.YearMonth;

public record DeviationTrendResource(
        YearMonth month,
        Long totalDeviations
) {
}