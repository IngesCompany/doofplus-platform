package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import lombok.Getter;

import java.util.Objects;

/**
 * Sensor Aggregate Root.
 *
 * <p>Represents an IoT telemetry sensor registered with ThingsBoard and installed on equipment.
 * Follows Domain-Driven Design principles with zero persistence or framework annotations.
 * Persistence concerns are handled exclusively by {@code SensorPersistenceEntity}.</p>
 */
@Getter
public class Sensor {
    private Long id;
    private SensorCode code;
    private DeviceId deviceId;
    private String type;
    private String unit;
    private String equipmentCode;
    private Double lowerLimit;
    private Double upperLimit;
    private String status;
    private String assignedBatch;

    /**
     * Creates a new Sensor aggregate from a create command.
     *
     * @param command creation command
     */
    public Sensor(CreateSensorCommand command) {
        this.code = Objects.requireNonNull(command.code(), "Sensor code must not be null");
        this.deviceId = Objects.requireNonNull(command.deviceId(), "Device ID must not be null");
        this.type = Objects.requireNonNull(command.type(), "Sensor type must not be null");
        this.unit = command.unit() != null ? command.unit() : "";
        this.equipmentCode = command.equipmentCode() != null ? command.equipmentCode() : "";
        this.lowerLimit = command.lowerLimit() != null ? command.lowerLimit() : 0.0;
        this.upperLimit = command.upperLimit() != null ? command.upperLimit() : 100.0;
        this.status = (command.status() != null && !command.status().isBlank()) ? command.status() : "active";
        this.assignedBatch = command.assignedBatch() != null ? command.assignedBatch() : "";
    }

    /**
     * Reconstitutes an existing Sensor aggregate from persistent state.
     *
     * @param id persistent ID
     * @param code sensor code
     * @param deviceId ThingsBoard device ID
     * @param type measurement type
     * @param unit unit of measurement
     * @param equipmentCode installed equipment code
     * @param lowerLimit lower threshold
     * @param upperLimit upper threshold
     * @param status sensor status
     * @param assignedBatch active batch code
     * @return reconstituted Sensor aggregate
     */
    public static Sensor reconstitute(
            Long id,
            SensorCode code,
            DeviceId deviceId,
            String type,
            String unit,
            String equipmentCode,
            Double lowerLimit,
            Double upperLimit,
            String status,
            String assignedBatch
    ) {
        var sensor = new Sensor(new CreateSensorCommand(
                code, deviceId, type, unit, equipmentCode, lowerLimit, upperLimit, status, assignedBatch
        ));
        sensor.id = id;
        return sensor;
    }

    /**
     * Updates this sensor aggregate from an update command.
     *
     * @param command update command
     * @return this updated aggregate
     */
    public Sensor update(UpdateSensorCommand command) {
        if (command.code() != null) this.code = command.code();
        if (command.deviceId() != null) this.deviceId = command.deviceId();
        if (command.type() != null) this.type = command.type();
        if (command.unit() != null) this.unit = command.unit();
        if (command.equipmentCode() != null) this.equipmentCode = command.equipmentCode();
        if (command.lowerLimit() != null) this.lowerLimit = command.lowerLimit();
        if (command.upperLimit() != null) this.upperLimit = command.upperLimit();
        if (command.status() != null) this.status = command.status();
        if (command.assignedBatch() != null) this.assignedBatch = command.assignedBatch();
        return this;
    }

    /**
     * Checks whether a telemetry reading value is out of acceptable limits.
     *
     * @param value measured value
     * @return true when below lowerLimit or above upperLimit
     */
    public boolean isOutOfRange(Double value) {
        if (value == null) return false;
        return value < lowerLimit || value > upperLimit;
    }

    /**
     * Assigns the sensor to feed readings to an active production batch.
     *
     * @param batchCode production batch code
     */
    public void assignBatch(String batchCode) {
        this.assignedBatch = batchCode != null ? batchCode : "";
    }
}
