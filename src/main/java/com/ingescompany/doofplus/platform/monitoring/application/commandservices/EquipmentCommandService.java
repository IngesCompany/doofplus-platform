package com.ingescompany.doofplus.platform.monitoring.application.commandservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.DeleteEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateEquipmentCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Equipment} commands.
 */
public interface EquipmentCommandService {
    Result<Equipment, ApplicationError> handle(CreateEquipmentCommand command);
    Result<Equipment, ApplicationError> handle(UpdateEquipmentCommand command);

    /**
     * Removes a piece of equipment; fails when it does not exist or one of its sensors is monitoring a batch.
     */
    Result<Equipment, ApplicationError> handle(DeleteEquipmentCommand command);
}
