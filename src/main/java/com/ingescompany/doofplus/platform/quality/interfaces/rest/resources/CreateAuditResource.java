package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a audit. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateAuditResource(
        String code,
        String title,
        String period,
        Integer areasReviewed,
        Integer areasTotal,
        String status) {
}
