package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.ApproveBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.ApproveBatchReleaseResource;

public class ApproveBatchReleaseCommandFromResourceAssembler {

    public static ApproveBatchReleaseCommand toCommandFromResource(
            Long releaseId,
            ApproveBatchReleaseResource resource) {

        return new ApproveBatchReleaseCommand(
                releaseId,
                resource.signedBy(),
                resource.signatureReference(),
                resource.decision()
        );
    }
}