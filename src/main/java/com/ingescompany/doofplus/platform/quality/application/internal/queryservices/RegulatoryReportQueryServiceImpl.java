package com.ingescompany.doofplus.platform.quality.application.internal.queryservices;

import com.ingescompany.doofplus.platform.quality.application.queryservices.RegulatoryReportQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllRegulatoryReportsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetRegulatoryReportByIdQuery;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IRegulatoryReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service of the regulatory reports.
 */
@Service
public class RegulatoryReportQueryServiceImpl implements RegulatoryReportQueryService {

    private final IRegulatoryReportRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the regulatory reports
     */
    public RegulatoryReportQueryServiceImpl(IRegulatoryReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RegulatoryReport> handle(GetAllRegulatoryReportsQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<RegulatoryReport> handle(GetRegulatoryReportByIdQuery query) {
        return repository.findById(query.regulatoryReportId());
    }
}
