package com.ingescompany.doofplus.platform.shared.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.net.URI;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProblemDetailFromApplicationErrorAssemblerTest {

    @AfterEach
    void clearLocale() {
        LocaleContextHolder.resetLocaleContext();
    }

    @Test
    void toResponseFromApplicationErrorUsesDefaultBundleMessage() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);

        var error = ApplicationError.notFound("Organization", "123");
        var response = ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        var problemDetail = response.getBody();

        assertNotNull(problemDetail);
        assertEquals(404, response.getStatusCode().value());
        assertEquals(404, problemDetail.getStatus());
        assertEquals("ORGANIZATION_NOT_FOUND", problemDetail.getProperties().get("code"));
        assertEquals("Resource not found", problemDetail.getDetail());
        assertEquals(URI.create("/api/v1/errors/not-found"), problemDetail.getType());
        assertEquals("Resource Not Found", problemDetail.getTitle());
    }

    @Test
    void toResponseFromApplicationErrorUsesSpanishBundleMessage() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));

        var error = ApplicationError.conflict("Organization", "RUC already registered");
        var response = ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        var problemDetail = response.getBody();

        assertNotNull(problemDetail);
        assertEquals(409, response.getStatusCode().value());
        assertEquals(409, problemDetail.getStatus());
        assertEquals("ORGANIZATION_CONFLICT", problemDetail.getProperties().get("code"));
        assertEquals("Conflicto de recurso", problemDetail.getDetail());
        assertEquals(URI.create("/api/v1/errors/conflict"), problemDetail.getType());
        assertEquals("Resource Conflict", problemDetail.getTitle());
    }

    @Test
    void toResponseFromApplicationErrorFallsBackToCategoryKeyWhenSpecificIsMissing() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));

        var error = ApplicationError.notFound("Batch", "42");
        var response = ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(error);
        var problemDetail = response.getBody();

        assertNotNull(problemDetail);
        assertEquals(404, response.getStatusCode().value());
        assertEquals(404, problemDetail.getStatus());
        assertEquals("BATCH_NOT_FOUND", problemDetail.getProperties().get("code"));
        assertEquals("Recurso no encontrado", problemDetail.getDetail());
        assertEquals(URI.create("/api/v1/errors/not-found"), problemDetail.getType());
    }

    @Test
    void toProblemDetailFromApplicationErrorTranslatesDetailsThatAreAMessageKey() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));

        var error = ApplicationError.validationError("request", "validation.request.failed");
        var problemDetail = ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(error);

        assertEquals("Validación de solicitud fallida", problemDetail.getProperties().get("details"));
    }

    @Test
    void toProblemDetailFromApplicationErrorKeepsDetailsThatAreFreeText() {
        LocaleContextHolder.setLocale(Locale.forLanguageTag("es"));

        var error = ApplicationError.validationError("request", "Quantity {0} is not valid");
        var problemDetail = ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(error);

        assertEquals("Quantity {0} is not valid", problemDetail.getProperties().get("details"));
    }
}
