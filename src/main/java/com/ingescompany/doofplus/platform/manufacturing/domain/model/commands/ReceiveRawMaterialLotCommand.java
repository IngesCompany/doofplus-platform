package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.MaterialCategory;

import java.time.LocalDate;

/**
 * Command to receive a raw material lot.
 *
 * @param code            unique internal lot code, for example {@code RM-26098}
 * @param receiptCode     receipt code, for example {@code RCV-26098}
 * @param material        material, for example {@code Paracetamol (API)}
 * @param category        category
 * @param supplier        supplier
 * @param supplierLot     lot of the supplier
 * @param quantity        quantity as the Web App writes it, for example {@code 500 kg · 20 sealed drums}
 * @param expiryDate      expiry date
 * @param storageLocation storage location
 * @param certificate     certificate of analysis file
 */
public record ReceiveRawMaterialLotCommand(String code, String receiptCode, String material, MaterialCategory category,
                                           String supplier, String supplierLot, String quantity, LocalDate expiryDate,
                                           String storageLocation, String certificate) {
    public ReceiveRawMaterialLotCommand {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("manufacturing.error.lot.code.notBlank");
        if (material == null || material.isBlank()) throw new IllegalArgumentException("manufacturing.error.lot.material.notBlank");
        if (category == null) throw new IllegalArgumentException("manufacturing.error.lot.category.invalid");
        if (expiryDate == null) throw new IllegalArgumentException("manufacturing.error.lot.expiryDate.invalid");
        code = code.trim();
    }
}
