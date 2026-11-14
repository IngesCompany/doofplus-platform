package com.ingescompany.doofplus.platform.shared.infrastructure.web.cors.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Cross-Origin Resource Sharing (CORS) for the REST API,
 * so the DoofPlus Web Application can call it from another origin.
 * <p>
 * It allows any origin, as the learning center platform does. When the IAM bounded context
 * adds Spring Security, this policy moves to its security filter chain.
 * </p>
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
