package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AnalyticalResultStatus;
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
@Table(name = "analytical_results")
@Getter
@Setter
@NoArgsConstructor
public class AnalyticalResultPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long batchId;

    @Column(nullable = false)
    private String testName;

    private String parameter;

    @Column(nullable = false)
    private Double measuredValue;

    @Column(nullable = false)
    private Double minimumValue;

    @Column(nullable = false)
    private Double maximumValue;

    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalyticalResultStatus status;

    private Long reviewedBy;

    @Column(nullable = false)
    private LocalDateTime testedAt;

    private LocalDateTime reviewedAt;
}