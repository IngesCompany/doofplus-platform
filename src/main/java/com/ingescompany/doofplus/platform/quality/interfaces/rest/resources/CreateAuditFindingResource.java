package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a audit finding. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateAuditFindingResource(
        String auditCode,
        String code,
        String title,
        String classification,
        String owner,
        String dueAt,
        String status,
        String observation,
        String response,
        String closureRequirement) {
}
