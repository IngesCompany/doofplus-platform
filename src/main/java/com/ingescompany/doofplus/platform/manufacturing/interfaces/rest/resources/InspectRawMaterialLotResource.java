package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to record the inspection of a raw material lot.
 */
public record InspectRawMaterialLotResource(
        Boolean packagingVerified,
        Boolean identityVerified,
        String samplingStatus,
        String storageLocation
) {
}
