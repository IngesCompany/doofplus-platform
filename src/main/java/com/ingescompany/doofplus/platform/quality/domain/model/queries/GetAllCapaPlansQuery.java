package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the capa plans, optionally filtered.
 *
 * @param sourceDeviation the source deviation to filter by, or null for all
 */
public record GetAllCapaPlansQuery(String sourceDeviation) {
}
