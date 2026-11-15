package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the audit findings, optionally filtered.
 *
 * @param auditCode the audit code to filter by, or null for all
 */
public record GetAllAuditFindingsQuery(String auditCode) {
}
