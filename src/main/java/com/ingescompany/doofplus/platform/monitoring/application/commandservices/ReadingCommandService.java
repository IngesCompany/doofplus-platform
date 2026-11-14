package com.ingescompany.doofplus.platform.monitoring.application.commandservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.RecordTelemetryCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Reading} commands.
 */
public interface ReadingCommandService {
    Result<Reading, ApplicationError> handle(CreateReadingCommand command);

    Result<Reading, ApplicationError> handle(RecordTelemetryCommand command);
}
