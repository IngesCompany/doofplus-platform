package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
/**
 * Represents a single IoT telemetry reading captured automatically from ThingsBoard.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "readings")

public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String sensorCode;
    @Column
    private String batchCode;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private String recordedAt;
    @Column(nullable = false)
    private String source;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;
    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;
    protected Reading() {}
    /**
     * Creates a telemetry reading from a create command.
     *
     * @param command creation command
     */
    public Reading(CreateReadingCommand command) {
        this.sensorCode = command.sensorCode();
        this.batchCode = command.batchCode() != null ? command.batchCode() : "";
        this.value = command.value();
        this.recordedAt = (command.recordedAt() != null && !command.recordedAt().isBlank())
                ? command.recordedAt()
                : Instant.now().toString();
        this.source = (command.source() != null && !command.source().isBlank())
                ? command.source()
                : "ThingsBoard";
    }
}