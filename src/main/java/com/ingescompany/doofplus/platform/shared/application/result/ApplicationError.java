package com.ingescompany.doofplus.platform.shared.application.result;

import org.jspecify.annotations.NullMarked;

/**
 * Represents an error that occurred in the application layer.
 * Designed to be easily mapped to HTTP responses and provide structured error information.
 *
 * @param code     A machine-readable error code (e.g., "ORGANIZATION_CONFLICT", "BATCH_NOT_FOUND")
 * @param message  A human-readable error message
 * @param details  Optional additional context about the error
 */
@NullMarked
public record ApplicationError(
        String code,
        String message,
        String details) {

    /**
     * Creates an ApplicationError with code and message only.
     */
    public ApplicationError(String code, String message) {
        this(code, message, null);
    }

    /**
     * Validation error: input data is invalid or violates constraints
     */
    public static ApplicationError validationError(String fieldOrConcept, String reason) {
        return new ApplicationError(
                "VALIDATION_ERROR",
                "Validation failed: %s".formatted(fieldOrConcept),
                reason);
    }

    /**
     * Not found error: the requested resource does not exist
     */
    public static ApplicationError notFound(String resourceType, String identifier) {
        return new ApplicationError(
                "%s_NOT_FOUND".formatted(resourceType.toUpperCase()),
                "%s not found: %s".formatted(resourceType, identifier),
                null);
    }

    /**
     * Business rule violation error: operation violates domain constraints
     */
    public static ApplicationError businessRuleViolation(String rule, String reason) {
        return new ApplicationError(
                "BUSINESS_RULE_VIOLATION",
                "Business rule violation: %s".formatted(rule),
                reason);
    }

    /**
     * Conflict error: operation cannot be completed due to conflicting state
     */
    public static ApplicationError conflict(String resource, String reason) {
        return new ApplicationError(
                "%s_CONFLICT".formatted(resource.toUpperCase()),
                "Conflict with %s".formatted(resource),
                reason);
    }

    /**
     * Invalid state error: the current status of the resource does not allow the operation (answered with 409)
     */
    public static ApplicationError invalidState(String resource, String reason) {
        return new ApplicationError(
                "%s_STATE_CONFLICT".formatted(resource.toUpperCase()),
                "The status of %s does not allow this operation".formatted(resource),
                reason);
    }

    /**
     * Unauthorized error: the caller did not send valid credentials
     */
    public static ApplicationError unauthorized(String context, String reason) {
        return new ApplicationError(
                "UNAUTHORIZED",
                "Unauthorized: %s".formatted(context),
                reason);
    }

    /**
     * Service unavailable error: an external service needed by the operation did not answer
     */
    public static ApplicationError serviceUnavailable(String service, String reason) {
        return new ApplicationError(
                "SERVICE_UNAVAILABLE",
                "Service unavailable: %s".formatted(service),
                reason);
    }

    /**
     * Unexpected error: something went wrong that shouldn't have
     */
    public static ApplicationError unexpected(String context, String reason) {
        return new ApplicationError(
                "UNEXPECTED_ERROR",
                "Unexpected error in %s".formatted(context),
                reason);
    }
}

