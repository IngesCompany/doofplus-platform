package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.UUID;

/**
 * Value object representing the alert code in the monitoring domain.
 */

public record AlertCode(String code) {
    /**
     * Default constructor.
     */
    public AlertCode { this(UUID.randomUUID().toString()); }

    /**
     * Compact constructor
     */
    public AlertCode {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Alert code cannot be null or empty");
        }
        try {
            UUID.fromString(code);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Alert code must be a valid UUID", exception);
        }
    }
}