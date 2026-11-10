package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchStatus;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.converters.BatchNumberPersistenceConverter;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.embeddables.QuantityPersistenceEmbeddable;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA Persistence entity for batches.
 */
@Entity
@Table(name="production_batches")
@Getter
@Setter
@NoArgsConstructor
public class ProductionBatchPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Convert(converter = BatchNumberPersistenceConverter.class)
    @Column(name="batch_number", nullable = false, unique = true, length = 30)
    private BatchNumber batchNumber;

    @Column(name = "production_order_id", nullable = false)
    private Long productionOrderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BatchStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "planned_quantity_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "planned_quantity_unit", length = 10))
    })
    private QuantityPersistenceEmbeddable plannedQuantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "manufactured_quantity_value")),
            @AttributeOverride(name = "unit", column = @Column(name = "manufactured_quantity_unit", length = 10))
    })
    private QuantityPersistenceEmbeddable manufacturedQuantity;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;
}
