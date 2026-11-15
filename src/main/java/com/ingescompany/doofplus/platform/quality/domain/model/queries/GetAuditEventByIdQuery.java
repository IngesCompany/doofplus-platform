package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a audit event by its identifier.
 *
 * @param auditEventId the identifier
 */
public record GetAuditEventByIdQuery(Long auditEventId) {
}
