package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Enumeration representing the Incident Severity.
 *
 * <p>
 * This enumeration is used to categorize incidents based on their impact and urgency.
 * </p>
 */
public enum IncidentSeverity {
    /**
     * The incident is of minor severity,
     * indicating a low impact on operations and requiring minimal attention.
     */
    MINOR,
    /**
     * The incident is of major severity,
     * indicating a significant impact on operations and requiring prompt attention.
     */
    MAJOR,
    /**
     * The incident is of critical severity,
     * indicating a severe impact on operations and requiring immediate attention.
     */
    CRITICAL
}
