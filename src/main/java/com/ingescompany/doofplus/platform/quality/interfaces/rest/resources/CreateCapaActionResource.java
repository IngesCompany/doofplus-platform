package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a capa action. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateCapaActionResource(
        String capaCode,
        String code,
        String type,
        String title,
        String owner,
        String dueAt,
        String status,
        String evidence,
        Boolean evidenceAttached) {
}
