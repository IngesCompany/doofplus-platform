package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.RejectBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RejectBatchReleaseResource;

public class RejectBatchReleaseCommandFromResourceAssembler {

    public static RejectBatchReleaseCommand toCommandFromResource(
            Long releaseId,
            RejectBatchReleaseResource resource) {

        return new RejectBatchReleaseCommand(
                releaseId,
                resource.signedBy(),
                resource.decision()
        );
    }
}