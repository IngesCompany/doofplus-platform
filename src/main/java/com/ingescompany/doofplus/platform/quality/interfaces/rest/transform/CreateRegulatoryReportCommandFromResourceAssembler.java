package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportType;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateRegulatoryReportResource;

/**
 * Converts the body to create a regulatory report to its command.
 */
public final class CreateRegulatoryReportCommandFromResourceAssembler {

    private CreateRegulatoryReportCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static CreateRegulatoryReportCommand toCommandFromResource(CreateRegulatoryReportResource resource) {
        return new CreateRegulatoryReportCommand(
                resource.code(),
                QualityValueParser.toEnum(ReportType.class, resource.type(), "quality.regulatoryReport.error.type.invalid"),
                resource.scope(),
                resource.owner(),
                QualityValueParser.toEnum(ReportStatus.class, resource.status(), "quality.regulatoryReport.error.status.invalid"),
                resource.template(),
                resource.format(),
                QualityValueParser.toDate(resource.createdAt(), "quality.regulatoryReport.error.createdAt.invalid"));
    }
}
