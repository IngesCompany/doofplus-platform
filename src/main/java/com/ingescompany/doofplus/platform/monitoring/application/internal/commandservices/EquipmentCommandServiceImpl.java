package com.ingescompany.doofplus.platform.monitoring.application.internal.commandservices;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.EquipmentCommandService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Equipment;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.DeleteEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.EquipmentRepository;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.SensorRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link EquipmentCommandService}.
 */
@Service
@Transactional
public class EquipmentCommandServiceImpl implements EquipmentCommandService {

    private final EquipmentRepository equipmentRepository;
    private final SensorRepository sensorRepository;

    public EquipmentCommandServiceImpl(EquipmentRepository equipmentRepository, SensorRepository sensorRepository) {
        this.equipmentRepository = equipmentRepository;
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Result<Equipment, ApplicationError> handle(CreateEquipmentCommand command) {
        if (equipmentRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("Equipment", "Equipment with code " + command.code().value() + " already exists"));
        }
        var equipment = new Equipment(command);
        var savedEquipment = equipmentRepository.save(equipment);
        return Result.success(savedEquipment);
    }

    @Override
    public Result<Equipment, ApplicationError> handle(UpdateEquipmentCommand command) {
        var existingEquipmentOpt = equipmentRepository.findById(command.id());
        if (existingEquipmentOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Equipment", command.id().toString()));
        }
        var equipment = existingEquipmentOpt.get();
        if (command.code() != null && !command.code().equals(equipment.getCode()) && equipmentRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("Equipment", "Equipment with code " + command.code().value() + " already exists"));
        }
        equipment.update(command);
        var updatedEquipment = equipmentRepository.save(equipment);
        return Result.success(updatedEquipment);
    }

    @Override
    public Result<Equipment, ApplicationError> handle(DeleteEquipmentCommand command) {
        var existingEquipmentOpt = equipmentRepository.findById(command.id());
        if (existingEquipmentOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Equipment", command.id().toString()));
        }
        var equipment = existingEquipmentOpt.get();
        if (sensorRepository.existsAssignedToBatchByEquipmentCode(equipment.getCode().value())) {
            return Result.failure(ApplicationError.conflict("Equipment",
                    "The equipment cannot be removed: one of its sensors is monitoring a production batch"));
        }
        equipmentRepository.deleteById(equipment.getId());
        return Result.success(equipment);
    }
}
