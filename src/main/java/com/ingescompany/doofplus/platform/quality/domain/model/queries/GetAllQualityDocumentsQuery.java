package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the quality documents, optionally filtered.
 *
 * @param code the code to filter by, or null for all
 */
public record GetAllQualityDocumentsQuery(String code) {
}
