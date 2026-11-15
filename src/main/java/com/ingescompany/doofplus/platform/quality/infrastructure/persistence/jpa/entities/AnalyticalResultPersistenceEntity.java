package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ApprovalState;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ResultStatus;
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
 * Row of the analytical_results table, as in db-06-quality.
 */
@Entity
@Table(name = "analytical_results")
@Getter
@Setter
@NoArgsConstructor
public class AnalyticalResultPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "batch_code", length = 30, nullable = false)
    private String batchCode;

    @Column(name = "sample_code", length = 30, nullable = false)
    private String sampleCode;

    @Column(name = "report_code", length = 30, nullable = false)
    private String reportCode;

    @Column(name = "test_name", length = 100, nullable = false)
    private String test;

    @Column(name = "protocol", length = 50, nullable = false)
    private String protocol;

    @Column(name = "result", length = 30, nullable = false)
    private String result;

    @Column(name = "specification", length = 50, nullable = false)
    private String specification;

    @Column(name = "analyst", length = 100, nullable = false)
    private String analyst;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private ResultStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval", length = 24, nullable = false)
    private ApprovalState approval;
}
