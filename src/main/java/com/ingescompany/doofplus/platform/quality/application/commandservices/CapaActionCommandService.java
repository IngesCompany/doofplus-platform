package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface CapaActionCommandService {
    Result<CapaAction, ApplicationError> handle(CreateCapaActionCommand command);
}