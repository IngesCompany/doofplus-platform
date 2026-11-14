package com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates;

import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

/**
 * Reading Aggregate Root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration support.
 * Follows Domain-Driven Design principles with zero persistence or framework annotations.
 * Persistence concerns are handled exclusively by {@code ReadingPersistenceEntity}.</p>
 */
@Getter
public class Reading extends AbstractDomainAggregateRoot<Reading> {
    private Long id;
    private String sensorCode;
    private String batchCode;
    private Double value;
    private String recordedAt;
    private String source;

    /**
     * Creates a new Reading aggregate from a create command.
     *
     * @param command creation command
     */
    public Reading(CreateReadingCommand command) {
        this.sensorCode = Objects.requireNonNull(command.sensorCode(), "Sensor code must not be null");
        this.batchCode = command.batchCode() != null ? command.batchCode() : "";
        this.value = Objects.requireNonNull(command.value(), "Reading value must not be null");
        this.recordedAt = (command.recordedAt() != null && !command.recordedAt().isBlank())
                ? command.recordedAt()
                : Instant.now().toString();
        this.source = (command.source() != null && !command.source().isBlank())
                ? command.source()
                : "ThingsBoard";
    }

    /**
     * Reconstitutes an existing Reading aggregate from persistent state.
     *
     * @param id persistent ID
     * @param sensorCode sensor code
     * @param batchCode batch code
     * @param value measured value
     * @param recordedAt time recorded (ISO 8601)
     * @param source source system
     * @return reconstituted Reading aggregate
     */
    public static Reading reconstitute(
            Long id,
            String sensorCode,
            String batchCode,
            Double value,
            String recordedAt,
            String source
    ) {
        var reading = new Reading(new CreateReadingCommand(sensorCode, batchCode, value, recordedAt, source));
        reading.id = id;
        return reading;
    }
}
