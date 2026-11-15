package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the regulatory reports.
 */
public interface RegulatoryReportCommandService {

    /**
     * Creates a regulatory report.
     *
     * @param command the data of the regulatory report
     * @return the created regulatory report, or a validation or conflict error
     */
    Result<RegulatoryReport, ApplicationError> handle(CreateRegulatoryReportCommand command);
}
