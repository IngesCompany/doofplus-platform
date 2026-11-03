package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
/**
 * Represents an alert raised when a telemetry reading exceeds acceptable limits
 * or equipment monitoring anomalies are detected.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "alerts", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }, name = Alert.ALERT_CODE_UNIQUE_CONSTRAINT) } )

public class Alert {
    public static final String ALERT_CODE_UNIQUE_CONSTRAINT = "uk_alert_code";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Convert(converter = AlertCodeAttributeConverter.class)
    private AlertCode code;
    @Column(nullable = false)
    private String sensorCode;
    @Column(nullable = false)
    private String equipmentCode;
    @Column(nullable = false)
    private String severity;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String detail;
    @Column
    private String batchCode;
    @Column
    private String acknowledgedBy;
    @Column
    private String acknowledgedAt;
    @Column(length = 1000)
    private String responseNote;
    @Column
    private String linkedRecord;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
    protected Alert() {}
    /**
     * Creates an alert aggregate from a create command.
     *
     * @param command creation command
     */
    public Alert(CreateAlertCommand command) {
        this.code = command.code();
        this.sensorCode = command.sensorCode();
        this.equipmentCode = command.equipmentCode();
        this.severity = command.severity() != null ? command.severity() : "warning";
        this.status = command.status() != null ? command.status() : "open";
        this.title = command.title();
        this.detail = command.detail();
        this.batchCode = command.batchCode() != null ? command.batchCode() : "";
        this.acknowledgedBy = command.acknowledgedBy() != null ? command.acknowledgedBy() : "";
        this.acknowledgedAt = command.acknowledgedAt() != null ? command.acknowledgedAt() : "";
        this.responseNote = command.responseNote() != null ? command.responseNote() : "";
        this.linkedRecord = command.linkedRecord() != null ? command.linkedRecord() : "";
    }

    /**
     * Updates this alert aggregate from an update command.
     *
     * @param command update command
     * @return updated alert
     */
    public Alert update(UpdateAlertCommand command) {
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