package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportType;
import java.time.LocalDate;

/**
 * Command to create a regulatory report. Null values take the defaults of the contract.
 */
public record CreateRegulatoryReportCommand(
        String code,
        ReportType type,
        String scope,
        String owner,
        ReportStatus status,
        String template,
        String format,
        LocalDate createdAt) {
}
