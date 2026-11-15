package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditResource;

/**
 * Converts a audit to its REST resource.
 */
public final class AuditResourceFromEntityAssembler {

    private AuditResourceFromEntityAssembler() {
    }

    /**
     * Converts a audit to its resource.
     *
     * @param audit the audit
     * @return the resource
     */
    public static AuditResource toResourceFromEntity(Audit audit) {
        return new AuditResource(
                audit.getId(),
                audit.getCode(),
                audit.getTitle(),
                audit.getPeriod(),
                audit.getAreasReviewed(),
                audit.getAreasTotal(),
                QualityValueParser.fromEnum(audit.getStatus()));
    }
}
