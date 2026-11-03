package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.CalibrationStatusAttributeConverter;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.EquipmentCodeAttributeConverter;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters.FacilityAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
/**
 * Represents a piece of manufacturing or laboratory equipment with its calibration
 * and preventive maintenance status.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "equipment", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "code" }, name = Equipment.EQUIPMENT_CODE_UNIQUE_CONSTRAINT)
})

public class Equipment {
    public static final String EQUIPMENT_CODE_UNIQUE_CONSTRAINT = "uk_equipment_code";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Convert(converter = EquipmentCodeAttributeConverter.class)
    private EquipmentCode code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    @Convert(converter = FacilityAttributeConverter.class)
    private Facility location;
    @Column(nullable = false)
    @Convert(converter = CalibrationStatusAttributeConverter.class)
    private CalibrationStatus calibrationStatus;
    @Column(nullable = false)
    private String calibrationDue;
    @Column(nullable = false)
    private String certificate;
    @Column(nullable = false)
    private String nextMaintenance;
    private String maintenanceOwner;
    @Column
    private String workOrder;
    @Column
    private String healthNote;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
    protected Equipment() {}
    /**
     * Creates an equipment aggregate from a create command.
     *
     * @param command creation command
     */
    public Equipment(CreateEquipmentCommand command) {
        this.code = command.code();
        this.name = command.name();
        this.model = command.model();
        this.location = command.location();
        this.calibrationStatus = command.calibrationStatus() != null ? command.calibrationStatus() : CalibrationStatus.FIT;
        this.calibrationDue = command.calibrationDue();
        this.certificate = command.certificate();
        this.nextMaintenance = command.nextMaintenance();
        this.maintenanceOwner = command.maintenanceOwner();
        this.workOrder = command.workOrder() != null ? command.workOrder() : "";
        this.healthNote = command.healthNote() != null ? command.healthNote() : "";
    }

    /**
     * Updates this equipment aggregate from an update command.
     *
     * @param command update command
     * @return updated equipment
     */
    public Equipment update(UpdateEquipmentCommand command) {
        if (command.name() != null) this.name = command.name();
        if (command.model() != null) this.model = command.model();
        if (command.location() != null) this.location = command.location();
        if (command.calibrationStatus() != null) this.calibrationStatus = command.calibrationStatus();
        if (command.calibrationDue() != null) this.calibrationDue = command.calibrationDue();
        if (command.certificate() != null) this.certificate = command.certificate();
        if (command.nextMaintenance() != null) this.nextMaintenance = command.nextMaintenance();
        if (command.maintenanceOwner() != null) this.maintenanceOwner = command.maintenanceOwner();
        if (command.workOrder() != null) this.workOrder = command.workOrder();
        if (command.healthNote() != null) this.healthNote = command.healthNote();
        return this;
    }

    /**
     * Checks whether the equipment is fit for production use.
     *
     * @return {@code true} when calibrationStatus is fit
     */
    public boolean isFitForUse() {
        return this.calibrationStatus == CalibrationStatus.FIT;
    }
}