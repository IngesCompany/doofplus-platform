package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.RegulatoryReportCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IRegulatoryReportRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the regulatory reports.
 */
@Service
public class RegulatoryReportCommandServiceImpl implements RegulatoryReportCommandService {

    private final IRegulatoryReportRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the regulatory reports
     */
    public RegulatoryReportCommandServiceImpl(IRegulatoryReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<RegulatoryReport, ApplicationError> handle(CreateRegulatoryReportCommand command) {
        try {
            var regulatoryReport = new RegulatoryReport(command);
            if (repository.existsByCode(regulatoryReport.getCode(), null)) {
                return Result.failure(ApplicationError.conflict("RegulatoryReport", "quality.regulatoryReport.error.duplicate"));
            }
            return Result.success(repository.save(regulatoryReport));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("RegulatoryReport", e.getMessage()));
        }
    }
}
