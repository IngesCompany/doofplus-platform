package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.DeviationResource;

/**
 * Converts a deviation to its REST resource.
 */
public final class DeviationResourceFromEntityAssembler {

    private DeviationResourceFromEntityAssembler() {
    }

    /**
     * Converts a deviation to its resource.
     *
     * @param deviation the deviation
     * @return the resource
     */
    public static DeviationResource toResourceFromEntity(Deviation deviation) {
        return new DeviationResource(
                deviation.getId(),
                deviation.getCode(),
                deviation.getTitle(),
                deviation.getBatchCode(),
                deviation.getOrderCode(),
                deviation.getSummary(),
                QualityValueParser.fromEnum(deviation.getClassification()),
                deviation.getContainment(),
                deviation.getSeverity(),
                deviation.getLikelihood(),
                deviation.getDetectability(),
                QualityValueParser.fromEnum(deviation.getStatus()),
                deviation.getRootCause(),
                deviation.getCapaCode(),
                deviation.getReportedBy(),
                QualityValueParser.fromDateTime(deviation.getReportedAt()),
                deviation.getOwner(),
                QualityValueParser.fromDateTime(deviation.getDueAt()));
    }
}
