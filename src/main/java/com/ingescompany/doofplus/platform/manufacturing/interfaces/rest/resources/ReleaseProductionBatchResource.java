package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to release a production batch.
 *
 * @param releasedBy name of the person who releases the batch
 */
public record ReleaseProductionBatchResource(String releasedBy) {
}
