package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateDeviationCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the deviations.
 */
public interface DeviationCommandService {

    /**
     * Creates a deviation.
     *
     * @param command the data of the deviation
     * @return the created deviation, or a validation or conflict error
     */
    Result<Deviation, ApplicationError> handle(CreateDeviationCommand command);

    /**
     * Replaces the data of a deviation.
     *
     * @param command the identifier and the new data
     * @return the changed deviation, or a not found, validation or conflict error
     */
    Result<Deviation, ApplicationError> handle(UpdateDeviationCommand command);
}
