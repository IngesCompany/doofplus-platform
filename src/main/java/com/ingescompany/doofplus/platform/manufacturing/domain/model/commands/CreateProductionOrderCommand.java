package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

/**
 * Command to plan a production order with an approved master formula.
 *
 * @param code            unique order code, for example {@code PO-26041}
 * @param masterFormulaId master formula identifier
 * @param batchCode       code of the batch it produces, for example {@code B-26041}
 * @param plannedQuantity planned quantity
 * @param line            production line
 * @param plannedStart    planned start
 * @param plannedEnd      planned end
 * @param operations      operations; the Web App can create the order without them
 */
public record CreateProductionOrderCommand(String code, Long masterFormulaId, String batchCode,
                                           BigDecimal plannedQuantity, String line, LocalDateTime plannedStart,
                                           LocalDateTime plannedEnd, List<OperationData> operations) {
    public CreateProductionOrderCommand {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("manufacturing.error.order.code.notBlank");
        if (masterFormulaId == null || masterFormulaId <= 0) throw new IllegalArgumentException("manufacturing.error.order.formulaId.invalid");
        if (plannedQuantity == null || plannedQuantity.signum() <= 0) throw new IllegalArgumentException("manufacturing.error.order.plannedQuantity.invalid");
        if (plannedStart != null && plannedEnd != null && plannedEnd.isBefore(plannedStart)) {
            throw new IllegalArgumentException("manufacturing.error.order.plannedDates.invalid");
        }
        operations = operations == null ? List.of() : List.copyOf(operations);
        if (operations.stream().map(OperationData::sequence).collect(java.util.stream.Collectors.toCollection(HashSet::new)).size() != operations.size()) {
            throw new IllegalArgumentException("manufacturing.error.order.sequence.duplicate");
        }
        code = code.trim();
    }
}
