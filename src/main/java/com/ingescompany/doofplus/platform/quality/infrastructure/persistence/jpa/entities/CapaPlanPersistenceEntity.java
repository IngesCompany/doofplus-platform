package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
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
 * Row of the capa_plans table, as in db-06-quality.
 */
@Entity
@Table(name = "capa_plans")
@Getter
@Setter
@NoArgsConstructor
public class CapaPlanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", length = 30, nullable = false, unique = true)
    private String code;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "source_deviation", length = 30, nullable = false)
    private String sourceDeviation;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Column(name = "reviewer", length = 100, nullable = false)
    private String reviewer;

    @Column(name = "root_cause", length = 150, nullable = false)
    private String rootCause;

    @Column(name = "acceptance_criterion", length = 500, nullable = false)
    private String acceptanceCriterion;

    @Column(name = "effectiveness_check_at")
    private LocalDate effectivenessCheckAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private CapaStatus status;
}
