package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to allocate a raw material lot to a batch.
 */
public record AllocateRawMaterialLotResource(
        String batchCode
) {
}
