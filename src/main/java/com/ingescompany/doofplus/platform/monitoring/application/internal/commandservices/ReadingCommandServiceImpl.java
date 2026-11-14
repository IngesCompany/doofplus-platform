package com.ingescompany.doofplus.platform.monitoring.application.internal.commandservices;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.ReadingCommandService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Sensor;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.RecordTelemetryCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.AlertRepository;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.ReadingRepository;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.SensorRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Records the readings of the sensors. A reading out of the range of its sensor opens an alert (US25).
 * The telemetry of ThingsBoard finds the sensor by its device and uses the batch the sensor monitors (TS04).
 */
@Slf4j
@Service
@Transactional
public class ReadingCommandServiceImpl implements ReadingCommandService {
    private static final String THINGSBOARD = "ThingsBoard";

    private final ReadingRepository readingRepository;
    private final SensorRepository sensorRepository;
    private final AlertRepository alertRepository;

    public ReadingCommandServiceImpl(ReadingRepository readingRepository, SensorRepository sensorRepository,
                                     AlertRepository alertRepository) {
        this.readingRepository = readingRepository;
        this.sensorRepository = sensorRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    public Result<Reading, ApplicationError> handle(CreateReadingCommand command) {
        var savedReading = readingRepository.save(new Reading(command));
        sensorRepository.findByCode(new SensorCode(command.sensorCode()))
                .ifPresent(sensor -> openAlertWhenOutOfRange(sensor, savedReading));
        return Result.success(savedReading);
    }

    @Override
    public Result<Reading, ApplicationError> handle(RecordTelemetryCommand command) {
        var sensor = sensorRepository.findByDeviceId(command.deviceId());
        if (sensor.isEmpty()) {
            log.warn("Telemetry of an unknown device: deviceId={}", command.deviceId().value());
            return Result.failure(ApplicationError.notFound("Sensor", command.deviceId().value()));
        }
        var reading = new Reading(new CreateReadingCommand(sensor.get().getCode().value(),
                sensor.get().getAssignedBatch(), command.value(), command.recordedAt(), THINGSBOARD));
        var savedReading = readingRepository.save(reading);
        openAlertWhenOutOfRange(sensor.get(), savedReading);
        return Result.success(savedReading);
    }

    private void openAlertWhenOutOfRange(Sensor sensor, Reading reading) {
        if (!sensor.isOutOfRange(reading.getValue())) return;
        var alert = Alert.fromOutOfRangeReading(sensor, reading);
        if (alertRepository.existsByCode(alert.getCode())) return;
        alertRepository.save(alert);
        log.info("Alert opened: code={}, sensor={}", alert.getCode().value(), sensor.getCode().value());
    }
}
