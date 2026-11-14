package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.RegulatoryReportCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IRegulatoryReportRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class RegulatoryReportCommandServiceImpl implements RegulatoryReportCommandService {

    private final IRegulatoryReportRepository regulatoryReportRepository;

    public RegulatoryReportCommandServiceImpl(IRegulatoryReportRepository regulatoryReportRepository) {
        this.regulatoryReportRepository = regulatoryReportRepository;
    }

    @Override
    public Result<RegulatoryReport, ApplicationError> handle(CreateRegulatoryReportCommand command) {
        try {
            if (regulatoryReportRepository.existsByCode(command.code())) {
                return Result.failure(
                        ApplicationError.conflict("regulatory report", "Report code already exists")
                );
            }

            RegulatoryReport report = new RegulatoryReport(
                    command.code(),
                    command.title(),
                    command.description(),
                    command.regulatoryAuthority(),
                    command.fileReference(),
                    command.dueDate(),
                    command.preparedBy()
            );

            return Result.success(regulatoryReportRepository.save(report));
        } catch (IllegalArgumentException exception) {
            return Result.failure(
                    ApplicationError.validationError("regulatory report", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("creating regulatory report", exception.getMessage())
            );
        }
    }
}