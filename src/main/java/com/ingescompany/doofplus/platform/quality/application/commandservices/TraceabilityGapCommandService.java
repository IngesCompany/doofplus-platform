package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface TraceabilityGapCommandService {
    Result<TraceabilityGap, ApplicationError> handle(CreateTraceabilityGapCommand command);
}