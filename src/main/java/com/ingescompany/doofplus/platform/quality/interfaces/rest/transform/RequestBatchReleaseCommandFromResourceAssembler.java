package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.RequestBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RequestBatchReleaseResource;

public class RequestBatchReleaseCommandFromResourceAssembler {

    public static RequestBatchReleaseCommand toCommandFromResource(
            RequestBatchReleaseResource resource) {

        return new RequestBatchReleaseCommand(
                resource.batchId(),
                resource.batchCode(),
                resource.requestedBy()
        );
    }
}