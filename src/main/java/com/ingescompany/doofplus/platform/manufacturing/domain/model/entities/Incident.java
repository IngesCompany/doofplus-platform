package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentSeverity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.IncidentStatus;
import lombok.Getter;

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

    public Incident() {
    }
    /**
     * Constructor for Incident.
     * @param description The description of the incident.
     * @param severity The severity level of the incident.
     */
    public Incident(String description, IncidentSeverity severity) {
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.severity = Objects.requireNonNull(severity, "severity must not be null");
        this.status = IncidentStatus.OPEN;
    }

    /**
     * Private constructor for reconstitution.
     */

    public static Incident reconstitute(Long id, String description, IncidentSeverity severity, IncidentStatus status) {
        Incident incident = new Incident();
        incident.id = id;
        incident.description = description;
        incident.severity = severity;
        incident.status = status;
        return incident;
    }

    /**
     * Escalates the incident to a higher severity level.
     */
    public void escalate() {
        this.status = IncidentStatus.ESCALATED;
    }

    /**
     * Resolves the incident and updates its status to RESOLVED.
     *
     * @param resolution The resolution details for the incident.
     */
    public void resolve(String resolution) {
        this.status = IncidentStatus.RESOLVED;
    }
}
