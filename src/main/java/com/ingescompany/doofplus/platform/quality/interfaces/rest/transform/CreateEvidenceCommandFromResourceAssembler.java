package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceType;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateEvidenceResource;

public class CreateEvidenceCommandFromResourceAssembler {

    public static CreateEvidenceCommand toCommandFromResource(
            CreateEvidenceResource resource) {

        return new CreateEvidenceCommand(
                resource.title(),
                resource.description(),
                EvidenceType.valueOf(resource.type().toUpperCase()),
                resource.fileReference(),
                resource.deviationId(),
                resource.documentId(),
                resource.uploadedBy()
        );
    }
}