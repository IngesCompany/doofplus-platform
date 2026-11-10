package com.ingescompany.doofplus.platform.monitoring.application.commandservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateEquipmentCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Equipment} commands.
 */
public interface EquipmentCommandService {
    Result<Equipment, ApplicationError> handle(CreateEquipmentCommand command);
    Result<Equipment, ApplicationError> handle(UpdateEquipmentCommand command);
}
