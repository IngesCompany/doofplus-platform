package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetRegulatoryReportByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Query service of the regulatory reports.
 */
public interface RegulatoryReportQueryService {

    /**
     * Lists the regulatory reports.
     *
     * @param query the query
     * @return the regulatory reports
     */
    List<RegulatoryReport> handle(GetAllRegulatoryReportsQuery query);

    /**
     * Finds a regulatory report by its identifier.
     *
     * @param query the query
     * @return the regulatory report, if it exists
     */
    Optional<RegulatoryReport> handle(GetRegulatoryReportByIdQuery query);
}
