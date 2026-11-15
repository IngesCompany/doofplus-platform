package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Quality document of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class QualityDocument extends AbstractDomainAggregateRoot<QualityDocument> {

    /**
     * The identifier; null until the quality document is saved.
     */
    private Long id;

    /**
     * The code.
     */
    private String code;

    /**
     * The version.
     */
    private String version;

    /**
     * The title.
     */
    private String title;

    /**
     * The status.
     */
    private DocumentStatus status;

    /**
     * The author.
     */
    private String author;

    /**
     * The technical reviewer.
     */
    private String technicalReviewer;

    /**
     * The approver.
     */
    private String approver;

    /**
     * The changed at.
     */
    private LocalDate changedAt;

    /**
     * The purpose.
     */
    private String purpose;

    /**
     * The scope.
     */
    private String scope;

    /**
     * The procedure.
     */
    private String procedure;

    /**
     * The revision summary.
     */
    private String revisionSummary;

    /**
     * The review cycle months.
     */
    private Integer reviewCycleMonths;

    private QualityDocument() {
    }

    /**
     * Creates the quality document from its command.
     *
     * @param command the data of the quality document
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public QualityDocument(CreateQualityDocumentCommand command) {
        apply(command.code(), command.version(), command.title(), command.status(), command.author(), command.technicalReviewer(), command.approver(), command.changedAt(), command.purpose(), command.scope(), command.procedure(), command.revisionSummary(), command.reviewCycleMonths());
    }

    /**
     * Rebuilds a saved quality document without validating it again.
     *
     * @return the quality document
     */
    public static QualityDocument reconstitute(Long id, String code, String version, String title, DocumentStatus status, String author, String technicalReviewer, String approver, LocalDate changedAt, String purpose, String scope, String procedure, String revisionSummary, Integer reviewCycleMonths) {
        var qualityDocument = new QualityDocument();
        qualityDocument.id = id;
        qualityDocument.code = code;
        qualityDocument.version = version;
        qualityDocument.title = title;
        qualityDocument.status = status;
        qualityDocument.author = author;
        qualityDocument.technicalReviewer = technicalReviewer;
        qualityDocument.approver = approver;
        qualityDocument.changedAt = changedAt;
        qualityDocument.purpose = purpose;
        qualityDocument.scope = scope;
        qualityDocument.procedure = procedure;
        qualityDocument.revisionSummary = revisionSummary;
        qualityDocument.reviewCycleMonths = reviewCycleMonths;
        return qualityDocument;
    }

    /**
     * Replaces the data of the quality document with the data of the command.
     *
     * @param command the new data
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public void update(UpdateQualityDocumentCommand command) {
        apply(command.code(), command.version(), command.title(), command.status(), command.author(), command.technicalReviewer(), command.approver(), command.changedAt(), command.purpose(), command.scope(), command.procedure(), command.revisionSummary(), command.reviewCycleMonths());
    }

    private void apply(String code, String version, String title, DocumentStatus status, String author, String technicalReviewer, String approver, LocalDate changedAt, String purpose, String scope, String procedure, String revisionSummary, Integer reviewCycleMonths) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.qualityDocument.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        if (version == null || version.isBlank()) throw new IllegalArgumentException("quality.qualityDocument.error.version.notBlank");
        this.version = version == null ? "" : version.trim();
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.qualityDocument.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.status = status != null ? status : DocumentStatus.DRAFT;
        this.author = author == null ? "" : author.trim();
        this.technicalReviewer = technicalReviewer == null ? "" : technicalReviewer.trim();
        this.approver = approver == null ? "" : approver.trim();
        this.changedAt = changedAt;
        this.purpose = purpose == null ? "" : purpose.trim();
        this.scope = scope == null ? "" : scope.trim();
        this.procedure = procedure == null ? "" : procedure.trim();
        this.revisionSummary = revisionSummary == null ? "" : revisionSummary.trim();
        this.reviewCycleMonths = reviewCycleMonths != null ? reviewCycleMonths : 12;
    }
}
