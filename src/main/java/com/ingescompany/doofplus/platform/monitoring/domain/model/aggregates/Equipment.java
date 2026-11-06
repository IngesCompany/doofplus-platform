package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateEquipmentCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;
import lombok.Getter;

import java.util.Objects;

/**
 * Equipment Aggregate Root.
 *
 * <p>Represents a piece of manufacturing or laboratory equipment with its calibration
 * and preventive maintenance status. Follows Domain-Driven Design principles with zero
 * persistence or framework annotations. Persistence concerns are handled exclusively by
 * {@code EquipmentPersistenceEntity}.</p>
 */
@Getter
public class Equipment {
    private Long id;
    private EquipmentCode code;
    private String name;
    private String model;
    private Facility location;
    private CalibrationStatus calibrationStatus;
    private String calibrationDue;
    private String certificate;
    private String nextMaintenance;
    private String maintenanceOwner;
    private String workOrder;
    private String healthNote;

    /**
     * Creates a new Equipment aggregate from a create command.
     *
     * @param command creation command
     */
    public Equipment(CreateEquipmentCommand command) {
        this.code = Objects.requireNonNull(command.code(), "Equipment code must not be null");
        this.name = Objects.requireNonNull(command.name(), "Equipment name must not be null");
        this.model = Objects.requireNonNull(command.model(), "Equipment model must not be null");
        this.location = Objects.requireNonNull(command.location(), "Equipment location must not be null");
        this.calibrationStatus = command.calibrationStatus() != null ? command.calibrationStatus() : CalibrationStatus.FIT;
        this.calibrationDue = command.calibrationDue() != null ? command.calibrationDue() : "";
        this.certificate = command.certificate() != null ? command.certificate() : "";
        this.nextMaintenance = command.nextMaintenance() != null ? command.nextMaintenance() : "";
        this.maintenanceOwner = command.maintenanceOwner() != null ? command.maintenanceOwner() : "";
        this.workOrder = command.workOrder() != null ? command.workOrder() : "";
        this.healthNote = command.healthNote() != null ? command.healthNote() : "";
    }

    /**
     * Reconstitutes an existing Equipment aggregate from persistent state.
     *
     * @param id persistent ID
     * @param code equipment code
     * @param name equipment name
     * @param model equipment model
     * @param location equipment location
     * @param calibrationStatus calibration status
     * @param calibrationDue calibration due date
     * @param certificate certificate code
     * @param nextMaintenance next maintenance date
     * @param maintenanceOwner maintenance owner
     * @param workOrder work order code
     * @param healthNote health note
     * @return reconstituted Equipment aggregate
     */
    public static Equipment reconstitute(
            Long id,
            EquipmentCode code,
            String name,
            String model,
            Facility location,
            CalibrationStatus calibrationStatus,
            String calibrationDue,
            String certificate,
            String nextMaintenance,
            String maintenanceOwner,
            String workOrder,
            String healthNote
    ) {
        var equipment = new Equipment(new CreateEquipmentCommand(
                code, name, model, location, calibrationStatus, calibrationDue, certificate,
                nextMaintenance, maintenanceOwner, workOrder, healthNote
        ));
        equipment.id = id;
        return equipment;
    }

    /**
     * Updates equipment details from an update command.
     *
     * @param command update command
     * @return this updated aggregate
     */
    public Equipment update(UpdateEquipmentCommand command) {
        if (command.code() != null) this.code = command.code();
        if (command.name() != null && !command.name().isBlank()) this.name = command.name();
        if (command.model() != null && !command.model().isBlank()) this.model = command.model();
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
     * Records a new calibration, marking the equipment fit for use.
     *
     * @param calibrationDue next due date (ISO 8601)
     * @param certificate calibration certificate code
     */
    public void recordCalibration(String calibrationDue, String certificate) {
        this.calibrationStatus = CalibrationStatus.FIT;
        this.calibrationDue = calibrationDue != null ? calibrationDue : "";
        this.certificate = certificate != null ? certificate : "";
    }

    /**
     * Records preventive maintenance for this equipment.
     *
     * @param nextMaintenance next maintenance date (ISO 8601)
     */
    public void recordMaintenance(String nextMaintenance) {
        this.nextMaintenance = nextMaintenance != null ? nextMaintenance : "";
        this.workOrder = "";
    }

    /**
     * Checks whether the equipment is fit for operation.
     *
     * @return true if fit
     */
    public boolean isFitForUse() {
        return this.calibrationStatus == CalibrationStatus.FIT;
    }
}
