package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

import java.util.regex.Pattern;
/**
 * Value object representing a Batch Number.
 *
 * <p>
 * This class encapsulates the concept of a batch number, ensuring that it adheres to specific validation rules.
 * </p>
 *
 * @param value the batch number value
 */
public record BatchNumber(String value) {
    private static final int MAX_LENGTH = 30;
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9-]+$");

    public BatchNumber {
        if (value == null || value.isBlank()){
            throw new IllegalArgumentException("manufacturing.error.batchNumber.notBlank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("manufacturing.error.batchNumber.size");
        }
        if (!PATTERN.matcher(value).matches()){
            throw new IllegalArgumentException("manufacturing.error.batchNumber.pattern");
        }
    }
}
