package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DocumentStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the quality_documents table, as in db-06-quality.
 */
@Entity
@Table(name = "quality_documents")
@Getter
@Setter
@NoArgsConstructor
public class QualityDocumentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", length = 30, nullable = false)
    private String code;

    @Column(name = "version", length = 10, nullable = false)
    private String version;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private DocumentStatus status;

    @Column(name = "author", length = 100, nullable = false)
    private String author;

    @Column(name = "technical_reviewer", length = 100, nullable = false)
    private String technicalReviewer;

    @Column(name = "approver", length = 100, nullable = false)
    private String approver;

    @Column(name = "changed_at")
    private LocalDate changedAt;

    @Column(name = "purpose", columnDefinition = "TEXT", nullable = false)
    private String purpose;

    @Column(name = "scope", columnDefinition = "TEXT", nullable = false)
    private String scope;

    @Column(name = "procedure_text", columnDefinition = "TEXT", nullable = false)
    private String procedure;

    @Column(name = "revision_summary", columnDefinition = "TEXT", nullable = false)
    private String revisionSummary;

    @Column(name = "review_cycle_months", nullable = false)
    private Integer reviewCycleMonths;
}
