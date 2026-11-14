package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Value object representing an alert code.
 *
 * @param value the alert code value (e.g. ALT-26031)
 */
public record AlertCode(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "monitoring.alert.error.code.notBlank";
    private static final String SIZE_MESSAGE_KEY = "monitoring.alert.error.code.size";
    private static final String PATTERN_MESSAGE_KEY = "monitoring.alert.error.code.pattern";

    public AlertCode {
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
