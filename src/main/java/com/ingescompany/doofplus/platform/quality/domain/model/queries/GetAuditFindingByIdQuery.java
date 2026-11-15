package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a audit finding by its identifier.
 *
 * @param auditFindingId the identifier
 */
public record GetAuditFindingByIdQuery(Long auditFindingId) {
}
