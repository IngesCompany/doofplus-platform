package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to receive a raw material lot. The expiry date uses yyyy-MM-dd.
 */
public record ReceiveRawMaterialLotResource(
        String code,
        String receiptCode,
        String material,
        String category,
        String supplier,
        String supplierLot,
        String quantity,
        String expiryDate,
        String storageLocation,
        String certificate
) {
}
