package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.ApproveBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.ApproveBatchReleaseResource;

public class ApproveBatchReleaseCommandFromResourceAssembler {

    public static ApproveBatchReleaseCommand toCommandFromResource(
            ApproveBatchReleaseResource resource) {

        return new ApproveBatchReleaseCommand(
                resource.releaseId(),
                resource.signedBy(),
                resource.signatureReference(),
                resource.decision()
        );
    }
}