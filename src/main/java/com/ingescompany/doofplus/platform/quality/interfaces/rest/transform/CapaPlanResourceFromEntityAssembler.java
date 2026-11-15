package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaPlanResource;

/**
 * Converts a capa plan to its REST resource.
 */
public final class CapaPlanResourceFromEntityAssembler {

    private CapaPlanResourceFromEntityAssembler() {
    }

    /**
     * Converts a capa plan to its resource.
     *
     * @param capaPlan the capa plan
     * @return the resource
     */
    public static CapaPlanResource toResourceFromEntity(CapaPlan capaPlan) {
        return new CapaPlanResource(
                capaPlan.getId(),
                capaPlan.getCode(),
                capaPlan.getTitle(),
                capaPlan.getSourceDeviation(),
                capaPlan.getOwner(),
                capaPlan.getReviewer(),
                capaPlan.getRootCause(),
                capaPlan.getAcceptanceCriterion(),
                QualityValueParser.fromDate(capaPlan.getEffectivenessCheckAt()),
                QualityValueParser.fromEnum(capaPlan.getStatus()));
    }
}
