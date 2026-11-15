package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CapaActionResource;

/**
 * Converts a capa action to its REST resource.
 */
public final class CapaActionResourceFromEntityAssembler {

    private CapaActionResourceFromEntityAssembler() {
    }

    /**
     * Converts a capa action to its resource.
     *
     * @param capaAction the capa action
     * @return the resource
     */
    public static CapaActionResource toResourceFromEntity(CapaAction capaAction) {
        return new CapaActionResource(
                capaAction.getId(),
                capaAction.getCapaCode(),
                capaAction.getCode(),
                QualityValueParser.fromEnum(capaAction.getType()),
                capaAction.getTitle(),
                capaAction.getOwner(),
                QualityValueParser.fromDate(capaAction.getDueAt()),
                QualityValueParser.fromEnum(capaAction.getStatus()),
                capaAction.getEvidence(),
                capaAction.getEvidenceAttached());
    }
}
