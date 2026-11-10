package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface RegulatoryReportCommandService {
    Result<RegulatoryReport, ApplicationError> handle(CreateRegulatoryReportCommand command);
}