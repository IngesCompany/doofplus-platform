package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a task comment. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateTaskCommentResource(
        String recordCode,
        String author,
        String initials,
        String postedAt,
        String text) {
}
