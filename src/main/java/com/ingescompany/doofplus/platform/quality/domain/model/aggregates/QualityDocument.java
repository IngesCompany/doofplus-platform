package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.QualityDocumentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_documents")
@NoArgsConstructor
public class QualityDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String documentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QualityDocumentStatus status;

    private String fileReference;

    private String createdBy;

    private LocalDateTime createdAt;

    public QualityDocument(String code, String title, String description,
                           String documentType, String fileReference,
                           String createdBy) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.documentType = documentType;
        this.fileReference = fileReference;
        this.createdBy = createdBy;
        this.status = QualityDocumentStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

    public void updateStatus(QualityDocumentStatus status) {
        this.status = status;
    }

    public void updateFileReference(String fileReference) {
        this.fileReference = fileReference;
    }
}