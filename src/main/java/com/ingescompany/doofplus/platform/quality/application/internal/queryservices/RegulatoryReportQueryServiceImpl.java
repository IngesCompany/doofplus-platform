package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetRegulatoryReportByIdQuery;
import com.ingescompany.doofplus.platform.quality.application.queryservices.RegulatoryReportQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IRegulatoryReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulatoryReportQueryServiceImpl implements RegulatoryReportQueryService {

    private final IRegulatoryReportRepository regulatoryReportRepository;

    public RegulatoryReportQueryServiceImpl(IRegulatoryReportRepository regulatoryReportRepository) {
        this.regulatoryReportRepository = regulatoryReportRepository;
    }

    @Override
    public List<RegulatoryReport> handle(GetAllRegulatoryReportsQuery query) {
        return regulatoryReportRepository.findAll();
    }

    @Override
    public Optional<RegulatoryReport> handle(GetRegulatoryReportByIdQuery query) {
        return regulatoryReportRepository.findById(query.id());
    }
}
