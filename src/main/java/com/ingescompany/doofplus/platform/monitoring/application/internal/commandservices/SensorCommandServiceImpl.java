package com.ingescompany.doofplus.platform.monitoring.application.internal.commandservices;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.SensorCommandService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.SensorRepository;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link SensorCommandService}.
 */
@Service
@Transactional
public class SensorCommandServiceImpl implements SensorCommandService {

    private final SensorRepository sensorRepository;

    public SensorCommandServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Result<Sensor, String> handle(CreateSensorCommand command) {
        if (sensorRepository.existsByCode(command.code())) {
            return Result.failure("Sensor with code " + command.code().value() + " already exists");
        }
        if (sensorRepository.existsByDeviceId(command.deviceId())) {
            return Result.failure("Sensor with deviceId " + command.deviceId().value() + " already registered");
        }
        var sensor = new Sensor(command);
        var savedSensor = sensorRepository.save(sensor);
        return Result.success(savedSensor);
    }

    @Override
    public Result<Sensor, String> handle(UpdateSensorCommand command) {
        var existingSensorOpt = sensorRepository.findById(command.id());
        if (existingSensorOpt.isEmpty()) {
            return Result.failure("Sensor with id " + command.id() + " not found");
        }
        var sensor = existingSensorOpt.get();
        if (command.code() != null && !command.code().equals(sensor.getCode()) && sensorRepository.existsByCode(command.code())) {
            return Result.failure("Sensor with code " + command.code().value() + " already exists");
        }
        if (command.deviceId() != null && !command.deviceId().equals(sensor.getDeviceId()) && sensorRepository.existsByDeviceId(command.deviceId())) {
            return Result.failure("Sensor with deviceId " + command.deviceId().value() + " already registered");
        }
        sensor.update(command);
        var updatedSensor = sensorRepository.save(sensor);
        return Result.success(updatedSensor);
    }
}
