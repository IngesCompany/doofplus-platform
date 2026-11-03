package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateSensorCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.DeviceIdAttributeConverter;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.SensorCodeAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

/**
 * Represents an IoT telemetry sensor registered with ThingsBoard and installed on equipment.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sensors", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" }, name = Sensor.SENSOR_CODE_UNIQUE_CONSTRAINT),
        @UniqueConstraint(columnNames = { "device_id" }, name = Sensor.DEVICE_ID_UNIQUE_CONSTRAINT)
})

public class Sensor {
    public static final String SENSOR_CODE_UNIQUE_CONSTRAINT = "uk_sensor_code";
    public static final String DEVICE_ID_UNIQUE_CONSTRAINT = "uk_sensor_device_id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Convert(converter = SensorCodeAttributeConverter.class)
    private SensorCode code;
    @Column(nullable = false)
    @Convert(converter = DeviceIdAttributeConverter.class)
    private DeviceId deviceId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private String equipmentCode;
    @Column(nullable = false)
    private Double lowerLimit;
    @Column(nullable = false)
    private Double upperLimit;
    @Column(nullable = false)
    private String status;
    @Column
    private String assignedBatch;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
    protected Sensor() {}
    /**
     * Creates a sensor aggregate from a create command.
     *
     * @param command creation command
     */
    public Sensor(CreateSensorCommand command) {
        this.code = command.code();
        this.deviceId = command.deviceId();
        this.type = command.type();
        this.unit = command.unit();
        this.equipmentCode = command.equipmentCode();
        this.lowerLimit = command.lowerLimit();
        this.upperLimit = command.upperLimit();
        this.status = command.status() != null ? command.status() : "active";
        this.assignedBatch = command.assignedBatch() != null ? command.assignedBatch() : "";
    }

    /**
     * Updates this sensor aggregate from an update command.
     *
     * @param command update command
     * @return updated sensor
     */
    public Sensor update(UpdateSensorCommand command) {
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
     * Checks whether a telemetry reading value is out of the acceptable range.
     *
     * @param value measured value
     * @return {@code true} when below lowerLimit or above upperLimit
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