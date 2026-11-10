package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.RegulatoryReportStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class RegulatoryReport extends AbstractDomainAggregateRoot<RegulatoryReport> {

    private Long id;
    private String code;
    private String title;
    private String description;
    private String regulatoryAuthority;
    private String fileReference;
    private LocalDate dueDate;
    private RegulatoryReportStatus status;
    private Long preparedBy;
    private LocalDateTime submittedAt;

    public RegulatoryReport(String code, String title, String description,
                            String regulatoryAuthority, String fileReference,
                            LocalDate dueDate, Long preparedBy) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Report code is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Report title is required");
        }

        this.code = code;
        this.title = title;
        this.description = description;
        this.regulatoryAuthority = regulatoryAuthority;
        this.fileReference = fileReference;
        this.dueDate = dueDate;
        this.preparedBy = preparedBy;
        this.status = RegulatoryReportStatus.DRAFT;
    }

    public static RegulatoryReport reconstitute(
            Long id, String code, String title, String description,
            String regulatoryAuthority, String fileReference,
            LocalDate dueDate, RegulatoryReportStatus status,
            Long preparedBy, LocalDateTime submittedAt) {

        RegulatoryReport report = new RegulatoryReport(
                code, title, description, regulatoryAuthority,
                fileReference, dueDate, preparedBy
        );
        report.id = id;
        report.status = status;
        report.submittedAt = submittedAt;
        return report;
    }

    public void submitForReview() {
        if (status != RegulatoryReportStatus.DRAFT) {
            throw new IllegalStateException("Only draft reports can be submitted for review");
        }
        status = RegulatoryReportStatus.UNDER_REVIEW;
    }

    public void approve() {
        if (status != RegulatoryReportStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Report must be under review");
        }
        status = RegulatoryReportStatus.APPROVED;
    }

    public void submit() {
        if (status != RegulatoryReportStatus.APPROVED) {
            throw new IllegalStateException("Only approved reports can be submitted");
        }
        status = RegulatoryReportStatus.SUBMITTED;
        submittedAt = LocalDateTime.now();
    }

    public void reject() {
        if (status != RegulatoryReportStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Only reports under review can be rejected");
        }
        status = RegulatoryReportStatus.REJECTED;
    }
}