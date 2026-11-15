package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the traceability gaps.
 */
public interface TraceabilityGapCommandService {

    /**
     * Creates a traceability gap.
     *
     * @param command the data of the traceability gap
     * @return the created traceability gap, or a validation error
     */
    Result<TraceabilityGap, ApplicationError> handle(CreateTraceabilityGapCommand command);
}
