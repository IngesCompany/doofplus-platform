package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources;

/**
 * Request resource to update the progress of a production batch.
 *
 * @param progress progress from 0 to 100
 */
public record UpdateBatchProgressResource(Integer progress) {
}
