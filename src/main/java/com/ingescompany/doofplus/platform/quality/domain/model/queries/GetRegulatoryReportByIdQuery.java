package com.ingescompany.doofplus.platform.quality.domain.model.queries;

/**
 * Query of a regulatory report by its identifier.
 *
 * @param regulatoryReportId the identifier
 */
public record GetRegulatoryReportByIdQuery(Long regulatoryReportId) {
}
