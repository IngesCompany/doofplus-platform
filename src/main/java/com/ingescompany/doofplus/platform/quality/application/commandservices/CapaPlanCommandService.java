package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaPlanCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the capa plans.
 */
public interface CapaPlanCommandService {

    /**
     * Creates a capa plan.
     *
     * @param command the data of the capa plan
     * @return the created capa plan, or a validation or conflict error
     */
    Result<CapaPlan, ApplicationError> handle(CreateCapaPlanCommand command);

    /**
     * Replaces the data of a capa plan.
     *
     * @param command the identifier and the new data
     * @return the changed capa plan, or a not found, validation or conflict error
     */
    Result<CapaPlan, ApplicationError> handle(UpdateCapaPlanCommand command);
}
