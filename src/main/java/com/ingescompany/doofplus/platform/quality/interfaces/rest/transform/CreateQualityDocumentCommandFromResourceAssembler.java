package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateQualityDocumentResource;

public class CreateQualityDocumentCommandFromResourceAssembler {

    public static CreateQualityDocumentCommand toCommandFromResource(
            CreateQualityDocumentResource resource) {

        return new CreateQualityDocumentCommand(
                resource.code(),
                resource.title(),
                resource.description(),
                resource.documentType(),
                resource.fileReference(),
                resource.version(),
                resource.ownerId()
        );
    }
}