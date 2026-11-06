package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Value object representing a sensor code.
 *
 * @param value the sensor code value
 */
public record SensorCode(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "monitoring.sensor.error.code.notBlank";
    private static final String SIZE_MESSAGE_KEY = "monitoring.sensor.error.code.size";
    private static final String PATTERN_MESSAGE_KEY = "monitoring.sensor.error.code.pattern";

    public SensorCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }
}
