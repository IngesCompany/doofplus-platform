package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to update a evidence. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record UpdateEvidenceResource(
        String recordCode,
        String fileName,
        String detail,
        String status) {
}
