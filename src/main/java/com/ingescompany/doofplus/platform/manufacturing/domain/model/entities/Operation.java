package com.ingescompany.doofplus.platform.manufacturing.domain.model.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.OperationData;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.UpdateOperationCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.OperationStatus;
import lombok.Getter;

/**
 * Operation of a production order, for example dispensing, granulation or coating.
 */
@Getter
public class Operation {
    private Long id;
    private int sequence;
    private String name;
    private String equipment;
    private int progress;
    private OperationStatus status;
    private String reference;

    private Operation() {
    }

    /**
     * Creates an operation that has not started.
     *
     * @param data data of the operation
     */
    public Operation(OperationData data) {
        this.sequence = data.sequence();
        this.name = data.name().trim();
        this.equipment = data.equipment() == null ? "" : data.equipment().trim();
        this.progress = 0;
        this.status = OperationStatus.NOT_STARTED;
        this.reference = "";
    }

    /**
     * Rebuilds a stored operation.
     *
     * @return the operation with its persistent identifier
     */
    public static Operation reconstitute(Long id, int sequence, String name, String equipment, int progress,
                                         OperationStatus status, String reference) {
        var operation = new Operation();
        operation.id = id;
        operation.sequence = sequence;
        operation.name = name;
        operation.equipment = equipment;
        operation.progress = progress;
        operation.status = status;
        operation.reference = reference;
        return operation;
    }

    /**
     * Records the progress of the operation.
     *
     * @param command new progress, status and reference
     */
    public void update(UpdateOperationCommand command) {
        this.progress = command.progress();
        this.status = command.status();
        this.reference = command.reference() == null ? "" : command.reference().trim();
    }
}
