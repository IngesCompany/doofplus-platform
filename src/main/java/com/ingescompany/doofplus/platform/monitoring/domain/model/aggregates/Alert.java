package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

/**
 * Alert Aggregate Root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration support.
 * Follows Domain-Driven Design principles with zero persistence or framework annotations.
 * Persistence concerns are handled exclusively by {@code AlertPersistenceEntity}.</p>
 */
@Getter
public class Alert extends AbstractDomainAggregateRoot<Alert> {
    private Long id;
    private AlertCode code;
    private String sensorCode;
    private String equipmentCode;
    private String severity;
    private String status;
    private String title;
    private String detail;
    private String batchCode;
    private String acknowledgedBy;
    private String acknowledgedAt;
    private String responseNote;
    private String linkedRecord;

    /**
     * Creates an alert aggregate from a create command.
     *
     * @param command creation command
     */
    public Alert(CreateAlertCommand command) {
        this.code = Objects.requireNonNull(command.code(), "Alert code must not be null");
        this.sensorCode = command.sensorCode() != null ? command.sensorCode() : "";
        this.equipmentCode = command.equipmentCode() != null ? command.equipmentCode() : "";
        this.severity = (command.severity() != null && !command.severity().isBlank()) ? command.severity() : "warning";
        this.status = (command.status() != null && !command.status().isBlank()) ? command.status() : "open";
        this.title = Objects.requireNonNull(command.title(), "Alert title must not be null");
        this.detail = command.detail() != null ? command.detail() : "";
        this.batchCode = command.batchCode() != null ? command.batchCode() : "";
        this.acknowledgedBy = command.acknowledgedBy() != null ? command.acknowledgedBy() : "";
        this.acknowledgedAt = command.acknowledgedAt() != null ? command.acknowledgedAt() : "";
        this.responseNote = command.responseNote() != null ? command.responseNote() : "";
        this.linkedRecord = command.linkedRecord() != null ? command.linkedRecord() : "";
    }

    /**
     * Reconstitutes an existing Alert aggregate from persistent state.
     *
     * @param id persistent ID
     * @param code alert code
     * @param sensorCode sensor code
     * @param equipmentCode equipment code
     * @param severity severity level
     * @param status alert status
     * @param title alert title
     * @param detail alert detail
     * @param batchCode batch code
     * @param acknowledgedBy who acknowledged
     * @param acknowledgedAt when acknowledged
     * @param responseNote corrective actions
     * @param linkedRecord linked deviation or CAPA
     * @return reconstituted Alert aggregate
     */
    public static Alert reconstitute(
            Long id,
            AlertCode code,
            String sensorCode,
            String equipmentCode,
            String severity,
            String status,
            String title,
            String detail,
            String batchCode,
            String acknowledgedBy,
            String acknowledgedAt,
            String responseNote,
            String linkedRecord
    ) {
        var alert = new Alert(new CreateAlertCommand(
                code, sensorCode, equipmentCode, severity, status, title, detail, batchCode,
                acknowledgedBy, acknowledgedAt, responseNote, linkedRecord
        ));
        alert.id = id;
        return alert;
    }

    /**
     * Updates this alert aggregate from an update command.
     *
     * @param command update command
     * @return this updated aggregate
     */
    public Alert update(UpdateAlertCommand command) {
        if (command.code() != null) this.code = command.code();
        if (command.sensorCode() != null) this.sensorCode = command.sensorCode();
        if (command.equipmentCode() != null) this.equipmentCode = command.equipmentCode();
        if (command.severity() != null) this.severity = command.severity();
        if (command.status() != null) this.status = command.status();
        if (command.title() != null) this.title = command.title();
        if (command.detail() != null) this.detail = command.detail();
        if (command.batchCode() != null) this.batchCode = command.batchCode();
        if (command.acknowledgedBy() != null) this.acknowledgedBy = command.acknowledgedBy();
        if (command.acknowledgedAt() != null) this.acknowledgedAt = command.acknowledgedAt();
        if (command.responseNote() != null) this.responseNote = command.responseNote();
        if (command.linkedRecord() != null) this.linkedRecord = command.linkedRecord();
        return this;
    }

    /**
     * Acknowledges the alert with user identification and corrective note.
     *
     * @param acknowledgedBy name of person acknowledging
     * @param responseNote corrective actions taken
     */
    public void acknowledge(String acknowledgedBy, String responseNote) {
        this.status = "acknowledged";
        this.acknowledgedBy = acknowledgedBy != null ? acknowledgedBy : "";
        this.acknowledgedAt = Instant.now().toString();
        if (responseNote != null) this.responseNote = responseNote;
    }

    /**
     * Links the alert to an incident or quality deviation.
     *
     * @param linkedRecord identifier of the linked record (e.g. DEV-26017)
     */
    public void linkRecord(String linkedRecord) {
        this.linkedRecord = linkedRecord != null ? linkedRecord : "";
        this.status = "under-investigation";
    }
}
