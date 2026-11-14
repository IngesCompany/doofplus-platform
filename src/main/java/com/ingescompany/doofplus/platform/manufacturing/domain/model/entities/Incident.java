package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Incident domain entity
 *
 * <p>
 *     Represents an incident that occurs during the manufacturing process.
 *     Incidents can be related to various aspects of the manufacturing process,
 *     such as equipment malfunctions, quality issues, or safety concerns.
 * </p>
 */
@Getter
public class Incident {

    /**
     * Unique identifier for the incident.
     */
    private Long id;

    /**
     * Description of the incident.
     */
    private String description;

    /**
     * Severity level of the incident.
     */
    private IncidentSeverity severity;

    /**
     * Status of the incident.
     */
    private IncidentStatus status;

    /**
     * Date and time when the incident was recorded.
     */
    private LocalDateTime recordedAt;

    public Incident() {
    }

    /**
     * Creates an open incident recorded now.
     *
     * @param description description of the incident
     * @param severity    severity of the incident
     */
    public Incident(String description, IncidentSeverity severity) {
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.severity = Objects.requireNonNull(severity, "severity must not be null");
        this.status = IncidentStatus.OPEN;
        this.recordedAt = LocalDateTime.now();
    }

    /**
     * Rebuilds a stored incident.
     *
     * @return the incident with its persistent identifier
     */
    public static Incident reconstitute(Long id, String description, IncidentSeverity severity, IncidentStatus status,
                                        LocalDateTime recordedAt) {
        Incident incident = new Incident();
        incident.id = id;
        incident.description = description;
        incident.severity = severity;
        incident.status = status;
        incident.recordedAt = recordedAt;
        return incident;
    }

    /**
     * Escalates the incident to Quality.
     */
    public void escalate() {
        this.status = IncidentStatus.ESCALATED;
    }

    /**
     * Resolves the incident.
     *
     * @param resolution description of the resolution
     */
    public void resolve(String resolution) {
        this.status = IncidentStatus.RESOLVED;
    }
}
