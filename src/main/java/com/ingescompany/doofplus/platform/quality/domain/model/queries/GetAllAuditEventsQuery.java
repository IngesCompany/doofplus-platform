package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the audit events, optionally filtered.
 *
 * @param record the record to filter by, or null for all
 */
public record GetAllAuditEventsQuery(String record) {
}
