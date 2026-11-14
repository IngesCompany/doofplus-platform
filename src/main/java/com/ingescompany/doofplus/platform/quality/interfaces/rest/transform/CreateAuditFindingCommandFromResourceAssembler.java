package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditFindingSeverity;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditFindingResource;

public class CreateAuditFindingCommandFromResourceAssembler {

    public static CreateAuditFindingCommand toCommandFromResource(
            CreateAuditFindingResource resource) {

        return new CreateAuditFindingCommand(
                resource.auditId(),
                resource.title(),
                resource.description(),
                AuditFindingSeverity.valueOf(resource.severity().toUpperCase()),
                resource.relatedDeviationId()
        );
    }
}