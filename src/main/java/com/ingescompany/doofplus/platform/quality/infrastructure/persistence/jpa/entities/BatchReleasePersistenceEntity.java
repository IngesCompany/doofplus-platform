package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.BatchReleaseStatus;
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
@Table(name = "batch_releases")
@Getter
@Setter
@NoArgsConstructor
public class BatchReleasePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long batchId;

    @Column(nullable = false)
    private String batchCode;

    @Column(nullable = false)
    private String requestedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchReleaseStatus status;

    @Column(columnDefinition = "TEXT")
    private String decision;

    private String signatureReference;

    private String signedBy;

    private LocalDateTime signedAt;
}