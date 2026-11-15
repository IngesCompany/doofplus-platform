package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Task comment answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record TaskCommentResource(
        Long id,
        String recordCode,
        String author,
        String initials,
        String postedAt,
        String text) {
}
