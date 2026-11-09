package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.ProductionBatchResource;

public class ProductionBatchResourceFromEntityAssembler {
    public static ProductionBatchResource toResourceFromEntity(ProductionBatch entity) {
        if (entity == null) return null;
        return new ProductionBatchResource(
                entity.getId(),
                entity.getBatchNumber() != null ? entity.getBatchNumber().value() : null,
                entity.getProductionOrderId(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getPlannedQuantity() != null ? entity.getPlannedQuantity().value() : null,
                entity.getPlannedQuantity() != null ? entity.getPlannedQuantity().unit() : null,
                entity.getManufacturedQuantity() != null ? entity.getManufacturedQuantity().value() : null,
                entity.getManufacturedQuantity() != null ? entity.getManufacturedQuantity().unit() : null,
                entity.getStartedAt() != null ? entity.getStartedAt().toString() : null,
                entity.getFinishedAt() != null ? entity.getFinishedAt().toString() : null
        );
    }
}
