package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateRegulatoryReportCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportType;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Getter;

/**
 * Regulatory report of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class RegulatoryReport extends AbstractDomainAggregateRoot<RegulatoryReport> {

    /**
     * The identifier; null until the regulatory report is saved.
     */
    private Long id;

    /**
     * The code.
     */
    private String code;

    /**
     * The type.
     */
    private ReportType type;

    /**
     * The scope.
     */
    private String scope;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The status.
     */
    private ReportStatus status;

    /**
     * The template.
     */
    private String template;

    /**
     * The format.
     */
    private String format;

    /**
     * The created at.
     */
    private LocalDate createdAt;

    private RegulatoryReport() {
    }

    /**
     * Creates the regulatory report from its command.
     *
     * @param command the data of the regulatory report
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public RegulatoryReport(CreateRegulatoryReportCommand command) {
        apply(command.code(), command.type(), command.scope(), command.owner(), command.status(), command.template(), command.format(), command.createdAt());
    }

    /**
     * Rebuilds a saved regulatory report without validating it again.
     *
     * @return the regulatory report
     */
    public static RegulatoryReport reconstitute(Long id, String code, ReportType type, String scope, String owner, ReportStatus status, String template, String format, LocalDate createdAt) {
        var regulatoryReport = new RegulatoryReport();
        regulatoryReport.id = id;
        regulatoryReport.code = code;
        regulatoryReport.type = type;
        regulatoryReport.scope = scope;
        regulatoryReport.owner = owner;
        regulatoryReport.status = status;
        regulatoryReport.template = template;
        regulatoryReport.format = format;
        regulatoryReport.createdAt = createdAt;
        return regulatoryReport;
    }

    private void apply(String code, ReportType type, String scope, String owner, ReportStatus status, String template, String format, LocalDate createdAt) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.regulatoryReport.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        this.type = type != null ? type : ReportType.QUALITY_REVIEW;
        this.scope = scope == null ? "" : scope.trim();
        this.owner = owner == null ? "" : owner.trim();
        this.status = status != null ? status : ReportStatus.DRAFT;
        this.template = template == null ? "" : template.trim();
        this.format = format == null ? "" : format.trim();
        this.createdAt = createdAt != null ? createdAt : LocalDate.now(ZoneOffset.UTC);
    }
}
