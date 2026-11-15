package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the deviations, optionally filtered.
 *
 * @param batchCode the batch code to filter by, or null for all
 */
public record GetAllDeviationsQuery(String batchCode) {
}
