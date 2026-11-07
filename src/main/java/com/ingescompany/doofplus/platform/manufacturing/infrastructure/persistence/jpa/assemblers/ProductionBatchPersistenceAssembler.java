package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.Quantity;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.embeddables.QuantityPersistenceEmbeddable;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.ProductionBatchPersistenceEntity;

import java.util.ArrayList;

/**
 * Static assembler between the domain and persistence representations.
 */
public final class ProductionBatchPersistenceAssembler {

    private ProductionBatchPersistenceAssembler() {
    }

    public static ProductionBatch toDomainFromPersistence(ProductionBatchPersistenceEntity entity) {
        if (entity==null) return null;

        Quantity plannedQty = entity.getPlannedQuantity() != null
                ? new Quantity(entity.getPlannedQuantity().getValue(), entity.getPlannedQuantity().getUnit())
                : null;
        Quantity manufacturedQty = entity.getManufacturedQuantity() != null
                ? new Quantity(entity.getManufacturedQuantity().getValue(), entity.getManufacturedQuantity().getUnit())
                : null;
        return ProductionBatch.reconstitute(
                entity.getId(),
                entity.getBatchNumber(),
                entity.getProductionOrderId(),
                entity.getStatus(),
                plannedQty,
                manufacturedQty,
                entity.getStartedAt(),
                entity.getFinishedAt(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
    public static ProductionBatchPersistenceEntity toPersistenceFromDomain(ProductionBatch domain) {
        if (domain == null) return null;
        ProductionBatchPersistenceEntity entity = new ProductionBatchPersistenceEntity();
        entity.setId(domain.getId());
        entity.setBatchNumber(domain.getBatchNumber());
        entity.setProductionOrderId(domain.getProductionOrderId());
        entity.setStatus(domain.getStatus());
        entity.setStartedAt(domain.getStartedAt());
        entity.setFinishedAt(domain.getFinishedAt());
        if (domain.getPlannedQuantity() != null) {
            entity.setPlannedQuantity(new QuantityPersistenceEmbeddable(
                    domain.getPlannedQuantity().value(),
                    domain.getPlannedQuantity().unit()
            ));
        }
        if (domain.getManufacturedQuantity() != null) {
            entity.setManufacturedQuantity(new QuantityPersistenceEmbeddable(
                    domain.getManufacturedQuantity().value(),
                    domain.getManufacturedQuantity().unit()
            ));
        }
        return entity;
    }
}
