package com.ingescompany.doofplus.platform.quality.application.queryservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;

import java.util.List;

public interface RegulatoryReportQueryService {
    List<RegulatoryReport> handle(GetAllRegulatoryReportsQuery query);
}