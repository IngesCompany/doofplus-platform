package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaActionCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the capa actions.
 */
public interface CapaActionCommandService {

    /**
     * Creates a capa action.
     *
     * @param command the data of the capa action
     * @return the created capa action, or a validation error
     */
    Result<CapaAction, ApplicationError> handle(CreateCapaActionCommand command);

    /**
     * Replaces the data of a capa action.
     *
     * @param command the identifier and the new data
     * @return the changed capa action, or a not found, validation error
     */
    Result<CapaAction, ApplicationError> handle(UpdateCapaActionCommand command);
}
