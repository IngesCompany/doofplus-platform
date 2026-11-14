package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.Product;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.ProductionOrder;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.RawMaterialLot;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.*;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.*;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.*;

import java.util.List;

import static com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform.CatalogValueParser.*;

/**
 * Converts the catalog resources to commands and the catalog aggregates to resources.
 */
public final class CatalogResourceAssembler {
    private static final String DOSAGE_FORM_INVALID = "manufacturing.error.product.dosageForm.invalid";

    private CatalogResourceAssembler() {
    }

    // ---------- products ----------
    public static CreateProductCommand toCommand(CreateProductResource resource) {
        return new CreateProductCommand(resource.code(), resource.name(),
                toEnum(DosageForm.class, resource.dosageForm(), DOSAGE_FORM_INVALID));
    }

    public static UpdateProductCommand toCommand(Long productId, UpdateProductResource resource) {
        return new UpdateProductCommand(productId, resource.name(),
                toEnum(DosageForm.class, resource.dosageForm(), DOSAGE_FORM_INVALID));
    }

    public static ProductResource toResource(Product product) {
        return new ProductResource(product.getId(), product.getCode(), product.getName(), product.getDosageForm().name());
    }

    // ---------- master formulas ----------
    public static CreateMasterFormulaCommand toCommand(CreateMasterFormulaResource resource) {
        List<FormulaItemData> items = resource.items() == null ? List.of() : resource.items().stream()
                .map(item -> new FormulaItemData(
                        toEnum(FormulaItemKind.class, item.kind(), "manufacturing.error.formula.itemKind.invalid"),
                        item.name(), item.role(), item.target(), item.tolerance(), item.control()))
                .toList();
        return new CreateMasterFormulaCommand(resource.productCode(), resource.version(), resource.batchSize(), items);
    }

    public static MasterFormulaResource toResource(MasterFormula formula) {
        return new MasterFormulaResource(formula.getId(), formula.getProductCode(), formula.getVersion(),
                formula.getStatus().name(), formula.getApprovedBy(), toText(formula.getApprovedAt()),
                formula.getBatchSize(),
                formula.getItems().stream()
                        .map(item -> new FormulaItemResource(item.getId(), item.getKind().name(), item.getName(),
                                item.getRole(), item.getTarget(), item.getTolerance(), item.getControl()))
                        .toList());
    }

    // ---------- production orders ----------
    public static CreateProductionOrderCommand toCommand(CreateProductionOrderResource resource) {
        List<OperationData> operations = resource.operations() == null ? List.of() : resource.operations().stream()
                .map(operation -> new OperationData(operation.sequence(), operation.name(), operation.equipment()))
                .toList();
        return new CreateProductionOrderCommand(resource.code(), resource.formulaId(), resource.batchCode(),
                resource.plannedQuantity(), resource.line(), toDateTime(resource.plannedStart()),
                toDateTime(resource.plannedEnd()), operations);
    }

    public static ChangeProductionOrderStatusCommand toCommand(Long orderId, ChangeProductionOrderStatusResource resource) {
        return new ChangeProductionOrderStatusCommand(orderId,
                toEnum(ProductionOrderStatus.class, resource.status(), "manufacturing.error.order.status.invalid"));
    }

    public static UpdateOperationCommand toCommand(Long orderId, Integer sequence, UpdateOperationResource resource) {
        return new UpdateOperationCommand(orderId, sequence, resource.progress(),
                toEnum(OperationStatus.class, resource.status(), "manufacturing.error.order.operationStatus.invalid"),
                resource.reference());
    }

    public static ProductionOrderResource toResource(ProductionOrder order) {
        return new ProductionOrderResource(order.getId(), order.getCode(), order.getProductCode(),
                order.getMasterFormulaId(), order.getBatchCode(), order.getPlannedQuantity(), order.getLine(),
                order.getStatus().name(), toText(order.getPlannedStart()), toText(order.getPlannedEnd()),
                order.getOperations().stream()
                        .map(operation -> new OperationResource(operation.getId(), operation.getSequence(),
                                operation.getName(), operation.getEquipment(), operation.getProgress(),
                                operation.getStatus().name(), operation.getReference()))
                        .toList());
    }

    // ---------- raw material lots ----------
    public static ReceiveRawMaterialLotCommand toCommand(ReceiveRawMaterialLotResource resource) {
        return new ReceiveRawMaterialLotCommand(resource.code(), resource.receiptCode(), resource.material(),
                toEnum(MaterialCategory.class, resource.category(), "manufacturing.error.lot.category.invalid"),
                resource.supplier(), resource.supplierLot(), resource.quantity(),
                toDate(resource.expiryDate(), "manufacturing.error.lot.expiryDate.invalid"),
                resource.storageLocation(), resource.certificate());
    }

    public static InspectRawMaterialLotCommand toCommand(Long lotId, InspectRawMaterialLotResource resource) {
        return new InspectRawMaterialLotCommand(lotId,
                Boolean.TRUE.equals(resource.packagingVerified()), Boolean.TRUE.equals(resource.identityVerified()),
                toEnum(SamplingStatus.class, resource.samplingStatus(), "manufacturing.error.lot.samplingStatus.invalid"),
                resource.storageLocation());
    }

    public static RawMaterialLotResource toResource(RawMaterialLot lot) {
        return new RawMaterialLotResource(lot.getId(), lot.getCode(), lot.getReceiptCode(), lot.getMaterial(),
                lot.getCategory().name(), lot.getSupplier(), lot.getSupplierLot(), lot.getQuantity(),
                toText(lot.getExpiryDate()), lot.getStorageLocation(), lot.getCertificate(), lot.getStatus().name(),
                lot.isPackagingVerified(), lot.isIdentityVerified(), lot.getSamplingStatus().name(),
                lot.getAllocatedBatch());
    }
}
