package com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Business key of a subscription plan, for example {@code standard-lab} or {@code enterprise}.
 *
 * @param value plan key in kebab case
 */
public record PlanKey(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[a-z0-9]+(-[a-z0-9]+)*$");
    private static final String NOT_BLANK_MESSAGE_KEY = "plan.error.planKey.notBlank";
    private static final String SIZE_MESSAGE_KEY = "plan.error.planKey.size";
    private static final String PATTERN_MESSAGE_KEY = "plan.error.planKey.pattern";

    public PlanKey {
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
