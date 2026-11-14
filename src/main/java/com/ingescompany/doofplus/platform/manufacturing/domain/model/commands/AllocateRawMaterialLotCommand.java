package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to allocate an approved raw material lot to a production batch.
 *
 * @param rawMaterialLotId lot identifier
 * @param batchCode        code of the batch, for example {@code B-26041}
 */
public record AllocateRawMaterialLotCommand(Long rawMaterialLotId, String batchCode) {
    public AllocateRawMaterialLotCommand {
        if (rawMaterialLotId == null || rawMaterialLotId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
        if (batchCode == null || batchCode.isBlank()) throw new IllegalArgumentException("manufacturing.error.lot.batch.notBlank");
        batchCode = batchCode.trim();
    }
}
