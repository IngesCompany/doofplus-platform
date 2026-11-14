package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.BatchEvent;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Incident;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.MaterialConsumption;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.BatchEventPersistenceEntity;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.IncidentPersistenceEntity;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.MaterialConsumptionPersistenceEntity;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionBatch;
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
                entity.getConsumptions().stream().map(ProductionBatchPersistenceAssembler::toDomain).toList(),
                new ArrayList<>(),
                entity.getIncidents().stream().map(ProductionBatchPersistenceAssembler::toDomain).toList(),
                entity.getEvents().stream().map(ProductionBatchPersistenceAssembler::toDomain).toList()
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
        domain.getConsumptions().forEach(consumption -> entity.getConsumptions().add(toPersistence(consumption)));
        domain.getIncidents().forEach(incident -> entity.getIncidents().add(toPersistence(incident)));
        domain.getEvents().forEach(event -> entity.getEvents().add(toPersistence(event)));
        return entity;
    }

    private static MaterialConsumption toDomain(MaterialConsumptionPersistenceEntity entity) {
        return MaterialConsumption.reconstitute(
                entity.getId(),
                entity.getRawMaterialLotId(),
                new Quantity(entity.getConsumedQuantity().getValue(), entity.getConsumedQuantity().getUnit()),
                entity.getConsumedAt());
    }

    private static MaterialConsumptionPersistenceEntity toPersistence(MaterialConsumption domain) {
        var entity = new MaterialConsumptionPersistenceEntity();
        entity.setId(domain.getId());
        entity.setRawMaterialLotId(domain.getRawMaterialLotId());
        entity.setConsumedQuantity(new QuantityPersistenceEmbeddable(
                domain.getConsumedQuantity().value(), domain.getConsumedQuantity().unit()));
        entity.setConsumedAt(domain.getConsumedAt());
        return entity;
    }

    private static Incident toDomain(IncidentPersistenceEntity entity) {
        return Incident.reconstitute(entity.getId(), entity.getDescription(), entity.getSeverity(),
                entity.getStatus(), entity.getRecordedAt());
    }

    private static IncidentPersistenceEntity toPersistence(Incident domain) {
        var entity = new IncidentPersistenceEntity();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        entity.setSeverity(domain.getSeverity());
        entity.setStatus(domain.getStatus());
        entity.setRecordedAt(domain.getRecordedAt());
        return entity;
    }

    private static BatchEvent toDomain(BatchEventPersistenceEntity entity) {
        return BatchEvent.reconstitute(entity.getId(), entity.getType(), entity.getDescription(),
                entity.getPerformedBy(), entity.getOccurredAt());
    }

    private static BatchEventPersistenceEntity toPersistence(BatchEvent domain) {
        var entity = new BatchEventPersistenceEntity();
        entity.setId(domain.getId());
        entity.setType(domain.getType());
        entity.setDescription(domain.getDescription());
        entity.setPerformedBy(domain.getPerformedBy());
        entity.setOccurredAt(domain.getOccurredAt());
        return entity;
    }
}
