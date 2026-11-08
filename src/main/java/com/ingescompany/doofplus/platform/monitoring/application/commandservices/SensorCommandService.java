package com.ingescompany.doofplus.platform.monitoring.application.commandservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Sensor} commands.
 */
public interface SensorCommandService {
    Result<Sensor, ApplicationError> handle(CreateSensorCommand command);
    Result<Sensor, ApplicationError> handle(UpdateSensorCommand command);
}
