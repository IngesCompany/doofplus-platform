package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to release a raw material lot for use.
 *
 * @param rawMaterialLotId lot identifier
 */
public record ApproveRawMaterialLotCommand(Long rawMaterialLotId) {
    public ApproveRawMaterialLotCommand {
        if (rawMaterialLotId == null || rawMaterialLotId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
    }
}
