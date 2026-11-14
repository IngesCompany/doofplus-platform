package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

/**
 * Value object representing a facility location.
 *
 * @param location the facility location
 */
public record Facility(String location) {
    private static final int MAX_LENGTH = 256;
    private static final String NOT_BLANK_ERROR_MESSAGE = "monitoring.equipment.error.location.notBlank";
    private static final String SIZE_ERROR_MESSAGE = "monitoring.equipment.error.location.size";

    public Facility {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_ERROR_MESSAGE);
        }
        if (location.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_ERROR_MESSAGE);
        }
    }

    public String value() {
        return location;
    }
}
