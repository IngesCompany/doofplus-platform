package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a regulatory report. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateRegulatoryReportResource(
        String code,
        String type,
        String scope,
        String owner,
        String status,
        String template,
        String format,
        String createdAt) {
}
