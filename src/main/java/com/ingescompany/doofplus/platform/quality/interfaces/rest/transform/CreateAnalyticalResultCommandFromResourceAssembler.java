package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateAnalyticalResultResource;

public class CreateAnalyticalResultCommandFromResourceAssembler {

    public static CreateAnalyticalResultCommand toCommandFromResource(
            CreateAnalyticalResultResource resource) {

        return new CreateAnalyticalResultCommand(
                resource.batchId(),
                resource.testName(),
                resource.parameter(),
                resource.measuredValue(),
                resource.minimumValue(),
                resource.maximumValue(),
                resource.unit(),
                resource.testedAt()
        );
    }
}