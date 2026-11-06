package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

/**
 * Enumeration representing equipment calibration status.
 */
public enum CalibrationStatus {
    FIT("fit"),
    DUE("due"),
    NOT_FIT("not-fit");

    private final String value;

    CalibrationStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static CalibrationStatus from(String value) {
        if (value == null || value.isBlank()) {
            return NOT_FIT;
        }
        for (CalibrationStatus status : values()) {
            if (status.value.equalsIgnoreCase(value) || status.name().equalsIgnoreCase(value.replace('-', '_'))) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown calibration status: " + value);
    }
}
