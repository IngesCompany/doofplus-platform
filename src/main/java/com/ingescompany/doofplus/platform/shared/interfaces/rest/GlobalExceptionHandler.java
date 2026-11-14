package com.ingescompany.doofplus.platform.shared.interfaces.rest;

import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Global exception handler for REST API.
 * Provides centralized exception handling for the entire application,
 * ensuring all unhandled exceptions are translated to consistent
 * RFC 9457 ProblemDetail HTTP responses.
 */
@RestControllerAdvice
@NullMarked
public class GlobalExceptionHandler {
    private static final String MESSAGES_BASENAME = "messages";

    /**
     * Reads the requested language and then the base bundle (English), without falling back to the language of
     * the machine. Otherwise an English request on a Spanish machine would get the Spanish messages.
     */
    private static final ResourceBundle.Control NO_FALLBACK_TO_SYSTEM_LOCALE =
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES);

    /**
     * Handles validation exceptions from Spring's request body validation.
     * Maps validation failure to a standardized RFC 9457 ProblemDetail response.
     *
     * @param ex the validation exception from @Valid binding
     * @return error response with BAD_REQUEST status and detailed invalid parameters
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getBindingResult().getFieldErrors();
        var validationPrefix = resolveMessageOrDefault("validation.field.prefix", "Field");
        var errorDetails = fieldErrors.isEmpty()
                ? resolveMessageOrDefault("validation.request.failed", "Request validation failed")
                : fieldErrors.stream()
                        .map(error -> "%s %s: %s".formatted(
                                validationPrefix,
                                error.getField(),
                                error.getDefaultMessage()
                        ))
                        .reduce((a, b) -> a + "; " + b)
                        .orElse(resolveMessageOrDefault("validation.request.failed", "Request validation failed"));

        var invalidParams = fieldErrors.stream()
                .map(err -> Map.of(
                        "field", err.getField(),
                        "message", err.getDefaultMessage() != null ? err.getDefaultMessage() : "Invalid value"
                ))
                .toList();

        var applicationError = ApplicationError.validationError("request-body", errorDetails);
        var problemDetail = ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(applicationError);
        if (!invalidParams.isEmpty()) {
            problemDetail.setProperty("invalidParams", invalidParams);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handles invalid request arguments such as malformed UUID path or payload values.
     * When the exception message is a key of the message bundle (value objects throw keys),
     * the details carry its localized text; any other message is returned as is.
     *
     * @param ex the illegal argument exception
     * @return error response with BAD_REQUEST status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        var applicationError = ApplicationError.validationError(
                resolveMessageOrDefault("validation.request.argument", "request-argument"),
                ex.getMessage() != null
                        ? resolveMessageOrDefault(ex.getMessage(), ex.getMessage())
                        : resolveMessageOrDefault("validation.request.failed", "Request validation failed")
        );
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(applicationError);
    }

    /**
     * Handles requests that leave out a required query parameter.
     *
     * @param ex the missing parameter exception
     * @return error response with BAD_REQUEST status and the name of the parameter
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ProblemDetail> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        var applicationError = ApplicationError.validationError(
                resolveMessageOrDefault("validation.request.argument", "request-argument"),
                resolveMessageOrDefault("validation.request.parameter.missing",
                        "Required parameter '%s' is missing".formatted(ex.getParameterName()),
                        ex.getParameterName())
        );
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(applicationError);
    }

    /**
     * Handles path or query parameters whose value cannot be converted to the expected type,
     * such as a text where a numeric id is expected.
     *
     * @param ex the type mismatch exception
     * @return error response with BAD_REQUEST status and the name of the parameter
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        var applicationError = ApplicationError.validationError(
                resolveMessageOrDefault("validation.request.argument", "request-argument"),
                resolveMessageOrDefault("validation.request.parameter.invalid",
                        "Parameter '%s' has an invalid value".formatted(ex.getName()),
                        ex.getName())
        );
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(applicationError);
    }

    /**
     * Handles request bodies that cannot be read, such as malformed JSON or a value of the wrong type.
     *
     * @param ex the unreadable message exception
     * @return error response with BAD_REQUEST status
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        var applicationError = ApplicationError.validationError(
                "request-body",
                resolveMessageOrDefault("validation.request.body.unreadable", "The request body is not valid JSON")
        );
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(applicationError);
    }

    /**
     * Handles unexpected runtime exceptions not caught by specific handlers.
     * Maps to a generic unexpected error response.
     *
     * @param ex the unhandled runtime exception
     * @return error response with INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException ex) {
        var applicationError = ApplicationError.unexpected(
                resolveMessageOrDefault("error.unexpected.context", "global-exception-handler"),
                ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred"
        );
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(applicationError);
    }

    /**
     * Handles all other exceptions not matched by specific handlers.
     * Provides a final fallback for any unexpected exception type.
     *
     * @param ex the exception
     * @return error response with INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        var applicationError = ApplicationError.unexpected(
                resolveMessageOrDefault("error.unexpected.context", "global-exception-handler"),
                ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred"
        );
        return ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(applicationError);
    }

    private String resolveMessageOrDefault(String key, String defaultValue, Object... args) {
        try {
            var bundle = ResourceBundle.getBundle(MESSAGES_BASENAME, LocaleContextHolder.getLocale(), NO_FALLBACK_TO_SYSTEM_LOCALE);
            if (!bundle.containsKey(key)) {
                return defaultValue;
            }
            return MessageFormat.format(bundle.getString(key), args);
        } catch (MissingResourceException ex) {
            return defaultValue;
        }
    }
}

