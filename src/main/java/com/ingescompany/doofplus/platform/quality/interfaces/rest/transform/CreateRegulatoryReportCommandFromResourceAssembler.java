package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateRegulatoryReportResource;

public class CreateRegulatoryReportCommandFromResourceAssembler {

    public static CreateRegulatoryReportCommand toCommandFromResource(
            CreateRegulatoryReportResource resource) {

        return new CreateRegulatoryReportCommand(
                resource.code(),
                resource.title(),
                resource.description(),
                resource.regulatoryAuthority(),
                resource.fileReference(),
                resource.dueDate(),
                resource.preparedBy()
        );
    }
}