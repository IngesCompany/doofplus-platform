package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.QualityDocumentStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QualityDocument extends AbstractDomainAggregateRoot<QualityDocument> {

    private Long id;
    private String code;
    private String title;
    private String description;
    private String documentType;
    private String fileReference;
    private String version;
    private QualityDocumentStatus status;
    private Long ownerId;
    private LocalDateTime approvedAt;

    public QualityDocument(String code, String title, String description,
                           String documentType, String fileReference,
                           String version, Long ownerId) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Document code is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Document title is required");
        }

        this.code = code;
        this.title = title;
        this.description = description;
        this.documentType = documentType;
        this.fileReference = fileReference;
        this.version = version;
        this.ownerId = ownerId;
        this.status = QualityDocumentStatus.DRAFT;
    }

    public static QualityDocument reconstitute(
            Long id, String code, String title, String description,
            String documentType, String fileReference, String version,
            QualityDocumentStatus status, Long ownerId, LocalDateTime approvedAt) {

        QualityDocument document = new QualityDocument(
                code, title, description, documentType,
                fileReference, version, ownerId
        );
        document.id = id;
        document.status = status;
        document.approvedAt = approvedAt;
        return document;
    }

    public void submitForReview() {
        if (status != QualityDocumentStatus.DRAFT) {
            throw new IllegalStateException("Only draft documents can be submitted");
        }
        status = QualityDocumentStatus.UNDER_REVIEW;
    }

    public void approve() {
        if (status != QualityDocumentStatus.UNDER_REVIEW) {
            throw new IllegalStateException("Document must be under review");
        }
        status = QualityDocumentStatus.APPROVED;
        approvedAt = LocalDateTime.now();
    }

    public void markObsolete() {
        if (status != QualityDocumentStatus.APPROVED) {
            throw new IllegalStateException("Only approved documents can become obsolete");
        }
        status = QualityDocumentStatus.OBSOLETE;
    }
}