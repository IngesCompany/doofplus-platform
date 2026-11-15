package com.ingescompany.doofplus.platform.quality.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetRegulatoryReportByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;

import java.util.List;

public interface RegulatoryReportQueryService {
    List<RegulatoryReport> handle(GetAllRegulatoryReportsQuery query);

    /**
     * Gets a RegulatoryReport by its identifier.
     *
     * @param query the query
     * @return the RegulatoryReport, or empty when it does not exist
     */
    Optional<RegulatoryReport> handle(GetRegulatoryReportByIdQuery query);
}
