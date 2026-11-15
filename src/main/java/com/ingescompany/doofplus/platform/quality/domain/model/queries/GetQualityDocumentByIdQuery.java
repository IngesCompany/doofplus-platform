package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a quality document by its identifier.
 *
 * @param qualityDocumentId the identifier
 */
public record GetQualityDocumentByIdQuery(Long qualityDocumentId) {
}
