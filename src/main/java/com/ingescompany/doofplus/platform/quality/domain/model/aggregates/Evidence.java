package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_evidence")
@NoArgsConstructor
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String fileReference;

    private String evidenceType;

    private UUID deviationId;

    private UUID auditId;

    private String uploadedBy;

    private LocalDateTime uploadedAt;

    public Evidence(String title, String description, String fileReference,
                    String evidenceType, UUID deviationId,
                    UUID auditId, String uploadedBy) {
        this.title = title;
        this.description = description;
        this.fileReference = fileReference;
        this.evidenceType = evidenceType;
        this.deviationId = deviationId;
        this.auditId = auditId;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = LocalDateTime.now();
    }

    public void updateFileReference(String fileReference) {
        this.fileReference = fileReference;
    }
}