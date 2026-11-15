package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a collaboration task. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateCollaborationTaskResource(
        String title,
        String recordCode,
        String detail,
        String owner,
        String dueAt,
        String status) {
}
