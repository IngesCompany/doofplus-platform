package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.CalibrationStatusAttributeConverter;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.EquipmentCodeAttributeConverter;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.FacilityAttributeConverter;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for Equipment.
 *
 * <p>Extends {@link AuditableAbstractPersistenceEntity} for ID and auditing timestamps.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "uk_equipment_code")
})
public class EquipmentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true, length = 64)
    @Convert(converter = EquipmentCodeAttributeConverter.class)
    private EquipmentCode code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 256)
    @Convert(converter = FacilityAttributeConverter.class)
    private Facility location;

    @Column(nullable = false, length = 32)
    @Convert(converter = CalibrationStatusAttributeConverter.class)
    private CalibrationStatus calibrationStatus;

    @Column(nullable = false)
    private String calibrationDue;

    @Column(nullable = false)
    private String certificate;

    @Column(nullable = false)
    private String nextMaintenance;

    @Column
    private String maintenanceOwner;

    @Column
    private String workOrder;

    @Column
    private String healthNote;
}
