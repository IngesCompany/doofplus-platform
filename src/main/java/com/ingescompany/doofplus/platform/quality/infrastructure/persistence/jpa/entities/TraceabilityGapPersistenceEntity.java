package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TraceabilityGapStatus;
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
@Table(name = "traceability_gaps")
@Getter
@Setter
@NoArgsConstructor
public class TraceabilityGapPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long batchId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String missingReference;

    private String affectedProcess;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TraceabilityGapStatus status;

    private Long reviewedBy;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    private LocalDateTime resolvedAt;
}