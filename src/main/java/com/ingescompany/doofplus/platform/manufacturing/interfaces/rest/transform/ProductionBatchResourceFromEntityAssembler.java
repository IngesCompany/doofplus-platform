package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.BatchEventResource;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.MaterialConsumptionResource;
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
                entity.getFinishedAt() != null ? entity.getFinishedAt().toString() : null,
                entity.getIncidents().stream().map(IncidentResourceFromEntityAssembler::toResourceFromEntity).toList(),
                entity.getConsumptions().stream()
                        .map(consumption -> new MaterialConsumptionResource(
                                consumption.getId(),
                                consumption.getRawMaterialLotId(),
                                consumption.getConsumedQuantity().value(),
                                consumption.getConsumedQuantity().unit(),
                                consumption.getConsumedAt() != null ? consumption.getConsumedAt().toString() : null))
                        .toList(),
                entity.getEvents().stream()
                        .map(event -> new BatchEventResource(
                                event.getId(),
                                event.getType(),
                                event.getDescription(),
                                event.getPerformedBy(),
                                event.getOccurredAt() != null ? event.getOccurredAt().toString() : null))
                        .toList()
        );
    }
}
