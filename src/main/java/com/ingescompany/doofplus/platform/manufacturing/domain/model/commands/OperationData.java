package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Data of an operation of a production order.
 *
 * @param sequence  order of the operation
 * @param name      name, for example {@code Granulation}
 * @param equipment equipment, for example {@code EQ-GRAN-01}
 */
public record OperationData(Integer sequence, String name, String equipment) {
    public OperationData {
        if (sequence == null || sequence <= 0) throw new IllegalArgumentException("manufacturing.error.order.sequence.invalid");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("manufacturing.error.order.operationName.notBlank");
    }
}
