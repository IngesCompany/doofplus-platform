package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Evidence extends AbstractDomainAggregateRoot<Evidence> {

    private Long id;
    private String title;
    private String description;
    private EvidenceType type;
    private String fileReference;
    private Long deviationId;
    private Long documentId;
    private Long uploadedBy;
    private EvidenceStatus status;
    private LocalDateTime uploadedAt;
    private LocalDateTime verifiedAt;

    public Evidence(String title, String description, EvidenceType type,
                    String fileReference, Long deviationId,
                    Long documentId, Long uploadedBy) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Evidence title is required");
        }
        if (type == null) {
            throw new IllegalArgumentException("Evidence type is required");
        }
        if (fileReference == null || fileReference.isBlank()) {
            throw new IllegalArgumentException("Evidence file reference is required");
        }

        this.title = title;
        this.description = description;
        this.type = type;
        this.fileReference = fileReference;
        this.deviationId = deviationId;
        this.documentId = documentId;
        this.uploadedBy = uploadedBy;
        this.status = EvidenceStatus.PENDING;
        this.uploadedAt = LocalDateTime.now();
    }

    public static Evidence reconstitute(
            Long id, String title, String description, EvidenceType type,
            String fileReference, Long deviationId, Long documentId,
            Long uploadedBy, EvidenceStatus status,
            LocalDateTime uploadedAt, LocalDateTime verifiedAt) {

        Evidence evidence = new Evidence(
                title, description, type, fileReference,
                deviationId, documentId, uploadedBy
        );
        evidence.id = id;
        evidence.status = status;
        evidence.uploadedAt = uploadedAt;
        evidence.verifiedAt = verifiedAt;
        return evidence;
    }

    public void verify() {
        if (status != EvidenceStatus.PENDING) {
            throw new IllegalStateException("Only pending evidence can be verified");
        }
        status = EvidenceStatus.VERIFIED;
        verifiedAt = LocalDateTime.now();
    }

    public void reject() {
        if (status != EvidenceStatus.PENDING) {
            throw new IllegalStateException("Only pending evidence can be rejected");
        }
        status = EvidenceStatus.REJECTED;
    }
}