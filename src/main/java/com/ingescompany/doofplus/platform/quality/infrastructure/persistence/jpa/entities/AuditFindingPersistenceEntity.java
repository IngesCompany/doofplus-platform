package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.FindingStatus;
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
 * Row of the audit_findings table, as in db-06-quality.
 */
@Entity
@Table(name = "audit_findings")
@Getter
@Setter
@NoArgsConstructor
public class AuditFindingPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "audit_code", length = 30, nullable = false)
    private String auditCode;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", length = 24, nullable = false)
    private Classification classification;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Column(name = "due_at")
    private LocalDate dueAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private FindingStatus status;

    @Column(name = "observation", columnDefinition = "TEXT", nullable = false)
    private String observation;

    @Column(name = "response", columnDefinition = "TEXT", nullable = false)
    private String response;

    @Column(name = "closure_requirement", columnDefinition = "TEXT", nullable = false)
    private String closureRequirement;
}
