package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.FormulaItem;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Operation;
import com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities.*;

/**
 * Converts the catalog aggregates (products, master formulas, production orders and raw material lots)
 * to and from their JPA entities.
 */
public final class CatalogPersistenceAssembler {

    private CatalogPersistenceAssembler() {
    }

    public static Product toDomain(ProductPersistenceEntity entity) {
        return Product.reconstitute(entity.getId(), entity.getCode(), entity.getName(), entity.getDosageForm());
    }

    public static ProductPersistenceEntity toPersistence(Product domain) {
        var entity = new ProductPersistenceEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        entity.setDosageForm(domain.getDosageForm());
        return entity;
    }

    public static MasterFormula toDomain(MasterFormulaPersistenceEntity entity) {
        return MasterFormula.reconstitute(entity.getId(), entity.getProductCode(), entity.getVersion(),
                entity.getStatus(), entity.getApprovedBy(), entity.getApprovedAt(), entity.getBatchSize(),
                entity.getItems().stream()
                        .map(item -> FormulaItem.reconstitute(item.getId(), item.getKind(), item.getName(),
                                item.getRole(), item.getTarget(), item.getTolerance(), item.getControl()))
                        .toList());
    }

    public static MasterFormulaPersistenceEntity toPersistence(MasterFormula domain) {
        var entity = new MasterFormulaPersistenceEntity();
        entity.setId(domain.getId());
        entity.setProductCode(domain.getProductCode());
        entity.setVersion(domain.getVersion());
        entity.setStatus(domain.getStatus());
        entity.setApprovedBy(domain.getApprovedBy());
        entity.setApprovedAt(domain.getApprovedAt());
        entity.setBatchSize(domain.getBatchSize());
        domain.getItems().forEach(item -> {
            var itemEntity = new FormulaItemPersistenceEntity();
            itemEntity.setId(item.getId());
            itemEntity.setKind(item.getKind());
            itemEntity.setName(item.getName());
            itemEntity.setRole(item.getRole());
            itemEntity.setTarget(item.getTarget());
            itemEntity.setTolerance(item.getTolerance());
            itemEntity.setControl(item.getControl());
            entity.getItems().add(itemEntity);
        });
        return entity;
    }

    public static ProductionOrder toDomain(ProductionOrderPersistenceEntity entity) {
        return ProductionOrder.reconstitute(entity.getId(), entity.getCode(), entity.getMasterFormulaId(),
                entity.getProductCode(), entity.getBatchCode(), entity.getLine(), entity.getPlannedQuantity(),
                entity.getStatus(), entity.getPlannedStart(), entity.getPlannedEnd(),
                entity.getOperations().stream()
                        .map(operation -> Operation.reconstitute(operation.getId(), operation.getSequence(),
                                operation.getName(), operation.getEquipment(), operation.getProgress(),
                                operation.getStatus(), operation.getReference()))
                        .toList());
    }

    public static ProductionOrderPersistenceEntity toPersistence(ProductionOrder domain) {
        var entity = new ProductionOrderPersistenceEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setMasterFormulaId(domain.getMasterFormulaId());
        entity.setProductCode(domain.getProductCode());
        entity.setBatchCode(domain.getBatchCode());
        entity.setLine(domain.getLine());
        entity.setPlannedQuantity(domain.getPlannedQuantity());
        entity.setStatus(domain.getStatus());
        entity.setPlannedStart(domain.getPlannedStart());
        entity.setPlannedEnd(domain.getPlannedEnd());
        domain.getOperations().forEach(operation -> {
            var operationEntity = new OperationPersistenceEntity();
            operationEntity.setId(operation.getId());
            operationEntity.setSequence(operation.getSequence());
            operationEntity.setName(operation.getName());
            operationEntity.setEquipment(operation.getEquipment());
            operationEntity.setProgress(operation.getProgress());
            operationEntity.setStatus(operation.getStatus());
            operationEntity.setReference(operation.getReference());
            entity.getOperations().add(operationEntity);
        });
        return entity;
    }

    public static RawMaterialLot toDomain(RawMaterialLotPersistenceEntity entity) {
        return RawMaterialLot.reconstitute(entity.getId(), entity.getCode(), entity.getReceiptCode(),
                entity.getMaterial(), entity.getCategory(), entity.getSupplier(), entity.getSupplierLot(),
                entity.getQuantity(), entity.getExpiryDate(), entity.getStorageLocation(), entity.getCertificate(),
                entity.getStatus(), entity.isPackagingVerified(), entity.isIdentityVerified(),
                entity.getSamplingStatus(), entity.getAllocatedBatch());
    }

    public static RawMaterialLotPersistenceEntity toPersistence(RawMaterialLot domain) {
        var entity = new RawMaterialLotPersistenceEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setReceiptCode(domain.getReceiptCode());
        entity.setMaterial(domain.getMaterial());
        entity.setCategory(domain.getCategory());
        entity.setSupplier(domain.getSupplier());
        entity.setSupplierLot(domain.getSupplierLot());
        entity.setQuantity(domain.getQuantity());
        entity.setExpiryDate(domain.getExpiryDate());
        entity.setStorageLocation(domain.getStorageLocation());
        entity.setCertificate(domain.getCertificate());
        entity.setStatus(domain.getStatus());
        entity.setPackagingVerified(domain.isPackagingVerified());
        entity.setIdentityVerified(domain.isIdentityVerified());
        entity.setSamplingStatus(domain.getSamplingStatus());
        entity.setAllocatedBatch(domain.getAllocatedBatch());
        return entity;
    }
}
