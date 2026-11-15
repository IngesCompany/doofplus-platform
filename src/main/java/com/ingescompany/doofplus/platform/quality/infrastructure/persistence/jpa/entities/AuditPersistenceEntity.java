package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the audits table, as in db-06-quality.
 */
@Entity
@Table(name = "audits")
@Getter
@Setter
@NoArgsConstructor
public class AuditPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", length = 30, nullable = false, unique = true)
    private String code;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "period", length = 40, nullable = false)
    private String period;

    @Column(name = "areas_reviewed", nullable = false)
    private Integer areasReviewed;

    @Column(name = "areas_total", nullable = false)
    private Integer areasTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private AuditStatus status;
}
