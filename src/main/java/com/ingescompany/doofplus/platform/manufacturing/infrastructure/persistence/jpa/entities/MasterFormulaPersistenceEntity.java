package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.FormulaStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity of the {@code master_formulas} table.
 */
@Entity
@Table(name = "master_formulas")
@Getter
@Setter
@NoArgsConstructor
public class MasterFormulaPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "product_code", nullable = false, length = 30)
    private String productCode;

    @Column(name = "version", nullable = false, length = 10)
    private String version;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FormulaStatus status;

    @Column(name = "batch_size", nullable = false, precision = 12, scale = 2)
    private BigDecimal batchSize;

    @Column(name = "approved_by", length = 100)
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDate approvedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "master_formula_id", nullable = false)
    @OrderBy("id")
    private List<FormulaItemPersistenceEntity> items = new ArrayList<>();
}
