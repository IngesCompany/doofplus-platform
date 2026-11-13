package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Email address that receives the invoices of the subscription.
 *
 * @param value email address
 */
public record BillingEmail(String value) {
    private static final int MAX_LENGTH = 254;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "subscription.error.billingEmail.notBlank";
    private static final String SIZE_MESSAGE_KEY = "subscription.error.billingEmail.size";
    private static final String PATTERN_MESSAGE_KEY = "subscription.error.billingEmail.pattern";

    public BillingEmail {
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
