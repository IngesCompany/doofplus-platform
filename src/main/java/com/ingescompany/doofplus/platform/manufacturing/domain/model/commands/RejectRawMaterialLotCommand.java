package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to reject a raw material lot.
 *
 * @param rawMaterialLotId lot identifier
 */
public record RejectRawMaterialLotCommand(Long rawMaterialLotId) {
    public RejectRawMaterialLotCommand {
        if (rawMaterialLotId == null || rawMaterialLotId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
    }
}
