package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AnalyticalResultResource;

/**
 * Converts a analytical result to its REST resource.
 */
public final class AnalyticalResultResourceFromEntityAssembler {

    private AnalyticalResultResourceFromEntityAssembler() {
    }

    /**
     * Converts a analytical result to its resource.
     *
     * @param analyticalResult the analytical result
     * @return the resource
     */
    public static AnalyticalResultResource toResourceFromEntity(AnalyticalResult analyticalResult) {
        return new AnalyticalResultResource(
                analyticalResult.getId(),
                analyticalResult.getBatchCode(),
                analyticalResult.getSampleCode(),
                analyticalResult.getReportCode(),
                analyticalResult.getTest(),
                analyticalResult.getProtocol(),
                analyticalResult.getResult(),
                analyticalResult.getSpecification(),
                analyticalResult.getAnalyst(),
                QualityValueParser.fromEnum(analyticalResult.getStatus()),
                QualityValueParser.fromEnum(analyticalResult.getApproval()));
    }
}
