package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a task comment by its identifier.
 *
 * @param taskCommentId the identifier
 */
public record GetTaskCommentByIdQuery(Long taskCommentId) {
}
