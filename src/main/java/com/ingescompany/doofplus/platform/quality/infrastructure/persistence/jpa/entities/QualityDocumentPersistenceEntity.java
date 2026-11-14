package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.QualityDocumentStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "quality_documents")
@Getter
@Setter
@NoArgsConstructor
public class QualityDocumentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String documentType;

    private String fileReference;

    private String version;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QualityDocumentStatus status;

    private Long ownerId;

    private LocalDateTime approvedAt;
}