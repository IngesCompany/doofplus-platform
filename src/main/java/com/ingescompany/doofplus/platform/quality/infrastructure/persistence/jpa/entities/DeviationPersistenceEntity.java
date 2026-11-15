package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the deviations table, as in db-06-quality.
 */
@Entity
@Table(name = "deviations")
@Getter
@Setter
@NoArgsConstructor
public class DeviationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", length = 30, nullable = false, unique = true)
    private String code;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "batch_code", length = 30, nullable = false)
    private String batchCode;

    @Column(name = "order_code", length = 30, nullable = false)
    private String orderCode;

    @Column(name = "summary", columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", length = 24, nullable = false)
    private Classification classification;

    @Column(name = "containment", columnDefinition = "TEXT", nullable = false)
    private String containment;

    @Column(name = "severity", nullable = false)
    private Integer severity;

    @Column(name = "likelihood", nullable = false)
    private Integer likelihood;

    @Column(name = "detectability", nullable = false)
    private Integer detectability;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private DeviationStatus status;

    @Column(name = "root_cause", length = 150, nullable = false)
    private String rootCause;

    @Column(name = "capa_code", length = 30, nullable = false)
    private String capaCode;

    @Column(name = "reported_by", length = 100, nullable = false)
    private String reportedBy;

    @Column(name = "reported_at")
    private LocalDateTime reportedAt;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Column(name = "due_at")
    private LocalDateTime dueAt;
}
