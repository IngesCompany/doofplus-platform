package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a traceability gap by its identifier.
 *
 * @param traceabilityGapId the identifier
 */
public record GetTraceabilityGapByIdQuery(Long traceabilityGapId) {
}
