package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationSeverity;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
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
@Table(name = "deviations")
@Getter
@Setter
@NoArgsConstructor
public class DeviationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviationSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviationStatus status;

    private Long batchId;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    private LocalDateTime closedAt;
}