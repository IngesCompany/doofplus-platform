package com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateProductionOrderCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateOperationCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.entities.Operation;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.ProductionOrderStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Production order: what to produce with an approved master formula, on which line and when.
 * It keeps the product and batch codes, as the Web App does.
 */
@Getter
public class ProductionOrder extends AbstractDomainAggregateRoot<ProductionOrder> {
    private Long id;
    private String code;
    private Long masterFormulaId;
    private String productCode;
    private String batchCode;
    private String line;
    private BigDecimal plannedQuantity;
    private ProductionOrderStatus status;
    private LocalDateTime plannedStart;
    private LocalDateTime plannedEnd;
    private final List<Operation> operations = new ArrayList<>();

    private ProductionOrder() {
    }

    /**
     * Plans a production order.
     *
     * @param command     data of the order
     * @param productCode code of the product of the master formula
     */
    public ProductionOrder(CreateProductionOrderCommand command, String productCode) {
        this.code = command.code();
        this.masterFormulaId = command.masterFormulaId();
        this.productCode = productCode;
        this.batchCode = command.batchCode() == null ? "" : command.batchCode().trim();
        this.line = command.line() == null ? "" : command.line().trim();
        this.plannedQuantity = command.plannedQuantity();
        this.status = ProductionOrderStatus.PLANNED;
        this.plannedStart = command.plannedStart();
        this.plannedEnd = command.plannedEnd();
        command.operations().stream()
                .sorted(Comparator.comparing(operation -> operation.sequence()))
                .forEach(operation -> this.operations.add(new Operation(operation)));
    }

    /**
     * Rebuilds a stored production order.
     *
     * @return the order with its persistent identifier
     */
    public static ProductionOrder reconstitute(Long id, String code, Long masterFormulaId, String productCode,
                                               String batchCode, String line, BigDecimal plannedQuantity,
                                               ProductionOrderStatus status, LocalDateTime plannedStart,
                                               LocalDateTime plannedEnd, List<Operation> operations) {
        var order = new ProductionOrder();
        order.id = id;
        order.code = code;
        order.masterFormulaId = masterFormulaId;
        order.productCode = productCode;
        order.batchCode = batchCode;
        order.line = line;
        order.plannedQuantity = plannedQuantity;
        order.status = status;
        order.plannedStart = plannedStart;
        order.plannedEnd = plannedEnd;
        if (operations != null) order.operations.addAll(operations);
        return order;
    }

    /**
     * Changes the status: planned → approved or in progress, approved → in progress,
     * in progress ↔ on hold, and in progress → completed.
     *
     * @param newStatus new status
     * @throws IllegalStateException when the change is not allowed
     */
    public void changeStatus(ProductionOrderStatus newStatus) {
        boolean allowed = switch (status) {
            case PLANNED -> newStatus == ProductionOrderStatus.APPROVED || newStatus == ProductionOrderStatus.IN_PROGRESS;
            case APPROVED -> newStatus == ProductionOrderStatus.IN_PROGRESS;
            case IN_PROGRESS -> newStatus == ProductionOrderStatus.ON_HOLD || newStatus == ProductionOrderStatus.COMPLETED;
            case ON_HOLD -> newStatus == ProductionOrderStatus.IN_PROGRESS;
            case COMPLETED -> false;
        };
        if (!allowed) throw new IllegalStateException("manufacturing.error.order.transition.invalid");
        this.status = newStatus;
    }

    /**
     * Records the progress of one operation.
     *
     * @param command sequence, progress, status and reference
     * @return {@code false} when the order has no operation with that sequence
     */
    public boolean updateOperation(UpdateOperationCommand command) {
        Optional<Operation> operation = operations.stream()
                .filter(candidate -> candidate.getSequence() == command.sequence())
                .findFirst();
        operation.ifPresent(found -> found.update(command));
        return operation.isPresent();
    }

    public List<Operation> getOperations() {
        return Collections.unmodifiableList(operations);
    }
}
