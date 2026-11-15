package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the evidence, optionally filtered.
 *
 * @param recordCode the record code to filter by, or null for all
 */
public record GetAllEvidenceQuery(String recordCode) {
}
