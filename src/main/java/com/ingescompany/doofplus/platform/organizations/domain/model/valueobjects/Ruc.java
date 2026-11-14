package com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Peruvian taxpayer number (RUC) that identifies an organization: exactly 11 digits.
 */
public record Ruc(String value) {
    private static final Pattern PATTERN = Pattern.compile("^\\d{11}$");

    public Ruc {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("organizations.error.ruc.notBlank");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("organizations.error.ruc.pattern");
        }
    }
}
