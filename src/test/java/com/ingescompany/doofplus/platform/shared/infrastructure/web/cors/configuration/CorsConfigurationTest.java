package com.ingescompany.doofplus.platform.shared.infrastructure.web.cors.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CorsConfigurationTest {

    /** Opens the protected getter of the registry to read what was registered. */
    private static class ReadableCorsRegistry extends CorsRegistry {
        Map<String, org.springframework.web.cors.CorsConfiguration> configurations() {
            return getCorsConfigurations();
        }
    }

    @Test
    void addCorsMappingsAllowsAnyOriginOnEveryPath() {
        var registry = new ReadableCorsRegistry();

        new CorsConfiguration().addCorsMappings(registry);
        var cors = registry.configurations().get("/**");

        assertNotNull(cors);
        assertEquals(List.of("*"), cors.getAllowedOrigins());
        assertEquals(List.of("*"), cors.getAllowedHeaders());
        assertTrue(Objects.requireNonNull(cors.getAllowedMethods())
                .containsAll(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")));
    }
}
