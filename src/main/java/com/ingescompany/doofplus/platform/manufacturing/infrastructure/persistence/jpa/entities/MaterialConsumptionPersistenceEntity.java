package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.embeddables.QuantityPersistenceEmbeddable;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA entity of the {@code material_consumptions} table. The {@code production_batch_id}
 * foreign key is mapped by {@link ProductionBatchPersistenceEntity}.
 */
@Entity
@Table(name = "material_consumptions")
@Getter
@Setter
@NoArgsConstructor
public class MaterialConsumptionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "raw_material_lot_id", nullable = false)
    private Long rawMaterialLotId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "consumed_quantity_value", precision = 12, scale = 4)),
            @AttributeOverride(name = "unit", column = @Column(name = "consumed_quantity_unit", length = 10))
    })
    private QuantityPersistenceEmbeddable consumedQuantity;

    @Column(name = "consumed_at", nullable = false)
    private LocalDateTime consumedAt;
}
