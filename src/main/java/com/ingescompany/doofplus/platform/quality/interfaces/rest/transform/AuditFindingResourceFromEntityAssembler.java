package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.AuditFindingResource;

/**
 * Converts a audit finding to its REST resource.
 */
public final class AuditFindingResourceFromEntityAssembler {

    private AuditFindingResourceFromEntityAssembler() {
    }

    /**
     * Converts a audit finding to its resource.
     *
     * @param auditFinding the audit finding
     * @return the resource
     */
    public static AuditFindingResource toResourceFromEntity(AuditFinding auditFinding) {
        return new AuditFindingResource(
                auditFinding.getId(),
                auditFinding.getAuditCode(),
                auditFinding.getCode(),
                auditFinding.getTitle(),
                QualityValueParser.fromEnum(auditFinding.getClassification()),
                auditFinding.getOwner(),
                QualityValueParser.fromDate(auditFinding.getDueAt()),
                QualityValueParser.fromEnum(auditFinding.getStatus()),
                auditFinding.getObservation(),
                auditFinding.getResponse(),
                auditFinding.getClosureRequirement());
    }
}
