package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.SamplingStatus;

/**
 * Command to record the QC inspection of a raw material lot.
 *
 * @param rawMaterialLotId  lot identifier
 * @param packagingVerified whether the packaging was verified
 * @param identityVerified  whether the identity was verified
 * @param samplingStatus    status of the sampling
 * @param storageLocation   new storage location, optional
 */
public record InspectRawMaterialLotCommand(Long rawMaterialLotId, boolean packagingVerified, boolean identityVerified,
                                           SamplingStatus samplingStatus, String storageLocation) {
    public InspectRawMaterialLotCommand {
        if (rawMaterialLotId == null || rawMaterialLotId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
        if (samplingStatus == null) throw new IllegalArgumentException("manufacturing.error.lot.samplingStatus.invalid");
    }
}
