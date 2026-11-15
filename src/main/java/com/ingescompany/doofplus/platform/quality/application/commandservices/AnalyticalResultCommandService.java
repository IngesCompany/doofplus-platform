package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the analytical results.
 */
public interface AnalyticalResultCommandService {

    /**
     * Creates a analytical result.
     *
     * @param command the data of the analytical result
     * @return the created analytical result, or a validation error
     */
    Result<AnalyticalResult, ApplicationError> handle(CreateAnalyticalResultCommand command);

    /**
     * Replaces the data of a analytical result.
     *
     * @param command the identifier and the new data
     * @return the changed analytical result, or a not found, validation error
     */
    Result<AnalyticalResult, ApplicationError> handle(UpdateAnalyticalResultCommand command);
}
