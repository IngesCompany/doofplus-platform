package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ReportType;
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
 * Row of the regulatory_reports table, as in db-06-quality.
 */
@Entity
@Table(name = "regulatory_reports")
@Getter
@Setter
@NoArgsConstructor
public class RegulatoryReportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", length = 30, nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 24, nullable = false)
    private ReportType type;

    @Column(name = "scope", length = 100, nullable = false)
    private String scope;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private ReportStatus status;

    @Column(name = "template", length = 100, nullable = false)
    private String template;

    @Column(name = "format", length = 100, nullable = false)
    private String format;

    @Column(name = "created_on")
    private LocalDate createdOn;
}
