package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * BatchEvent domain entity
 *
 * <p>
 *     Represents an event that occurs during the lifecycle of a production batch.
 *     This entity is responsible for tracking significant events related to the
 *     production batch, such as status changes, incidents, or other noteworthy occurrences.
 * </p>
 */
@Getter
public class BatchEvent {

    /**
     * Unique identifier for the batch event.
     */
    private Long id;

    /**
     * Type of the batch event (e.g., "STATUS_CHANGE", "INCIDENT_REPORTED").
     */
    private String type;

    /**
     * Description of the batch event, providing additional context or details.
     */
    private String description;

    /**
     * Identifier of the user or system that performed the action leading to this event.
     */
    private Long performedBy;

    /**
     * Timestamp indicating when the event occurred.
     */
    private LocalDateTime occurredAt;

    public BatchEvent() {
    }

    /**
     * Constructor for BatchEvent.
     * @param type The type of the batch event.
     * @param description A description of the batch event.
     * @param performedBy The identifier of the user or system that performed the action.
     */
    public BatchEvent(String type, String description, Long performedBy) {
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.performedBy = performedBy;
        this.occurredAt = LocalDateTime.now();
    }

    /**
     * Private constructor for reconstitution.
     */
    public static BatchEvent reconstitute(Long id, String type, String description, Long performedBy, LocalDateTime occurredAt) {
        BatchEvent event = new BatchEvent();
        event.id = id;
        event.type = type;
        event.description = description;
        event.performedBy = performedBy;
        event.occurredAt = occurredAt;
        return event;
    }
}
