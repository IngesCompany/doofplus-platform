package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of the capa actions, optionally filtered.
 *
 * @param capaCode the capa code to filter by, or null for all
 */
public record GetAllCapaActionsQuery(String capaCode) {
}
