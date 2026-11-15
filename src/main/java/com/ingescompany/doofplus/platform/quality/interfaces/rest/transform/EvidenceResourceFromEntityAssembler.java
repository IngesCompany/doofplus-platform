package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.EvidenceResource;

/**
 * Converts a evidence to its REST resource.
 */
public final class EvidenceResourceFromEntityAssembler {

    private EvidenceResourceFromEntityAssembler() {
    }

    /**
     * Converts a evidence to its resource.
     *
     * @param evidence the evidence
     * @return the resource
     */
    public static EvidenceResource toResourceFromEntity(Evidence evidence) {
        return new EvidenceResource(
                evidence.getId(),
                evidence.getRecordCode(),
                evidence.getFileName(),
                evidence.getDetail(),
                QualityValueParser.fromEnum(evidence.getStatus()));
    }
}
