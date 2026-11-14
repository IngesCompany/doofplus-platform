package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.DeviceIdAttributeConverter;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.SensorCodeAttributeConverter;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for Sensor.
 *
 * <p>Extends {@link AuditableAbstractPersistenceEntity} for ID and auditing timestamps.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sensors", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "uk_sensor_code"),
        @UniqueConstraint(columnNames = {"device_id"}, name = "uk_sensor_device_id")
})
public class SensorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true, length = 64)
    @Convert(converter = SensorCodeAttributeConverter.class)
    private SensorCode code;

    @Column(name = "device_id", nullable = false, unique = true, length = 128)
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
}
