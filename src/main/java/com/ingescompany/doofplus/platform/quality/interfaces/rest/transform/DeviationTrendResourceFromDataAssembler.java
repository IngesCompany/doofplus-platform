package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationTrendResource;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class DeviationTrendResourceFromDataAssembler {

    public static List<DeviationTrendResource> toResourcesFromData(
            Map<YearMonth, Long> trends) {

        return trends.entrySet()
                .stream()
                .map(entry -> new DeviationTrendResource(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();
    }
}