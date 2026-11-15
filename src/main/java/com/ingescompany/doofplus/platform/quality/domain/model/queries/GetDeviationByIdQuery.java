package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a deviation by its identifier.
 *
 * @param deviationId the identifier
 */
public record GetDeviationByIdQuery(Long deviationId) {
}
