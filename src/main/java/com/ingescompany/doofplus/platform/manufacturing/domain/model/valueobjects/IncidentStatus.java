package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Enumeration representing the Incident Status.
 *
 * <p>
 *     This enum defines the possible statuses for an incident in
 *     the manufacturing domain.
 * </p>
 */
public enum IncidentStatus {
    /**
     * The incident is currently open and requires attention.
     */
    OPEN,
    /**
     * The incident has been escalated for further review or action.
     */
    ESCALATED,
    /**
     * The incident has been resolved.
     */
    RESOLVED
}
