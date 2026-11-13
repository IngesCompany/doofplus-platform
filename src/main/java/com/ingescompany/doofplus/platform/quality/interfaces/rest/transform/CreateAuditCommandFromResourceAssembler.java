package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAuditResource;

public class CreateAuditCommandFromResourceAssembler {

    public static CreateAuditCommand toCommandFromResource(CreateAuditResource resource) {
        return new CreateAuditCommand(
                resource.code(),
                resource.title(),
                resource.scope(),
                resource.auditType(),
                resource.auditorId(),
                resource.plannedDate()
        );
    }
}