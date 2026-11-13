package com.ingescompany.doofplus.platform.shared.interfaces.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.Locale;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @AfterEach
    void clearLocale() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void handleRuntimeExceptionUsesLocalizedUnexpectedMessage() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));

        var handler = new GlobalExceptionHandler();
        var response = handler.handleRuntimeException(new RuntimeException("boom"));
        var problemDetail = Objects.requireNonNull(response.getBody());

        assertEquals(500, response.getStatusCode().value());
        assertEquals(500, problemDetail.getStatus());
        assertEquals("UNEXPECTED_ERROR", problemDetail.getProperties().get("code"));
        assertEquals("Error inesperado", problemDetail.getDetail());
        assertEquals("boom", problemDetail.getProperties().get("details"));
        assertEquals(URI.create("/api/v1/errors/unexpected-error"), problemDetail.getType());
    }

    @Test
    void handleIllegalArgumentExceptionReturnsValidationError() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        var handler = new GlobalExceptionHandler();
        var response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("RUC must have 11 digits")
        );
        var problemDetail = Objects.requireNonNull(response.getBody());

        assertEquals(400, response.getStatusCode().value());
        assertEquals(400, problemDetail.getStatus());
        assertEquals("VALIDATION_ERROR", problemDetail.getProperties().get("code"));
        assertEquals("Validation failed", problemDetail.getDetail());
        assertEquals("RUC must have 11 digits", problemDetail.getProperties().get("details"));
        assertEquals(URI.create("/api/v1/errors/validation-error"), problemDetail.getType());
    }

    @Test
    void handleIllegalArgumentExceptionTranslatesMessageKey() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));
        var handler = new GlobalExceptionHandler();
        var response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("validation.request.failed")
        );
        var problemDetail = Objects.requireNonNull(response.getBody());

        assertEquals(400, response.getStatusCode().value());
        assertEquals("VALIDATION_ERROR", problemDetail.getProperties().get("code"));
        assertEquals("Validación de solicitud fallida", problemDetail.getProperties().get("details"));
    }

    @Test
    void handleIllegalArgumentExceptionKeepsFreeTextMessage() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));
        var handler = new GlobalExceptionHandler();
        var response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("Invalid UUID string: {abc}")
        );
        var problemDetail = Objects.requireNonNull(response.getBody());

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid UUID string: {abc}", problemDetail.getProperties().get("details"));
    }
}
