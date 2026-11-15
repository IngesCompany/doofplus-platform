package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RegulatoryReportResource;

/**
 * Converts a regulatory report to its REST resource.
 */
public final class RegulatoryReportResourceFromEntityAssembler {

    private RegulatoryReportResourceFromEntityAssembler() {
    }

    /**
     * Converts a regulatory report to its resource.
     *
     * @param regulatoryReport the regulatory report
     * @return the resource
     */
    public static RegulatoryReportResource toResourceFromEntity(RegulatoryReport regulatoryReport) {
        return new RegulatoryReportResource(
                regulatoryReport.getId(),
                regulatoryReport.getCode(),
                QualityValueParser.fromEnum(regulatoryReport.getType()),
                regulatoryReport.getScope(),
                regulatoryReport.getOwner(),
                QualityValueParser.fromEnum(regulatoryReport.getStatus()),
                regulatoryReport.getTemplate(),
                regulatoryReport.getFormat(),
                QualityValueParser.fromDate(regulatoryReport.getCreatedAt()));
    }
}
