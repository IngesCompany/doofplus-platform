package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.GapStatus;
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
 * Row of the traceability_gaps table, as in db-06-quality.
 */
@Entity
@Table(name = "traceability_gaps")
@Getter
@Setter
@NoArgsConstructor
public class TraceabilityGapPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "batch_code", length = 30, nullable = false)
    private String batchCode;

    @Column(name = "product", length = 100, nullable = false)
    private String product;

    @Column(name = "missing_record", length = 150, nullable = false)
    private String missingRecord;

    @Column(name = "detail", length = 500, nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private GapStatus status;
}
