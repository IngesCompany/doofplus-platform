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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "line", length = 30)
    private String line;

    @Column(name = "owner", length = 150)
    private String owner;

    @Column(name = "progress", nullable = false)
    private int progress;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "production_batch_id", nullable = false)
    @OrderBy("id")
    private List<MaterialConsumptionPersistenceEntity> consumptions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "production_batch_id", nullable = false)
    @OrderBy("id")
    private List<IncidentPersistenceEntity> incidents = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "production_batch_id", nullable = false)
    @OrderBy("id")
    private List<BatchEventPersistenceEntity> events = new ArrayList<>();
}
