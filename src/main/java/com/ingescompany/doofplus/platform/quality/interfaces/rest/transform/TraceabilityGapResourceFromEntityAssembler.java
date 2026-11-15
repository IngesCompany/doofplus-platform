package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.TraceabilityGapResource;

/**
 * Converts a traceability gap to its REST resource.
 */
public final class TraceabilityGapResourceFromEntityAssembler {

    private TraceabilityGapResourceFromEntityAssembler() {
    }

    /**
     * Converts a traceability gap to its resource.
     *
     * @param traceabilityGap the traceability gap
     * @return the resource
     */
    public static TraceabilityGapResource toResourceFromEntity(TraceabilityGap traceabilityGap) {
        return new TraceabilityGapResource(
                traceabilityGap.getId(),
                traceabilityGap.getBatchCode(),
                traceabilityGap.getProduct(),
                traceabilityGap.getMissingRecord(),
                traceabilityGap.getDetail(),
                QualityValueParser.fromEnum(traceabilityGap.getStatus()));
    }
}
