package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Response resource representing a raw material lot.
 */
public record RawMaterialLotResource(
        Long id,
        String code,
        String receiptCode,
        String material,
        String category,
        String supplier,
        String supplierLot,
        String quantity,
        String expiryDate,
        String storageLocation,
        String certificate,
        String status,
        boolean packagingVerified,
        boolean identityVerified,
        String samplingStatus,
        String allocatedBatch
) {
}
