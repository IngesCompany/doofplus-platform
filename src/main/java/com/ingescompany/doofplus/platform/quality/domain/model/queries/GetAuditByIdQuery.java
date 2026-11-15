package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a audit by its identifier.
 *
 * @param auditId the identifier
 */
public record GetAuditByIdQuery(Long auditId) {
}
