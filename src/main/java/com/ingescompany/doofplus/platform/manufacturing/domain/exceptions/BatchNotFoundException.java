package com.ingescompany.doofplus.platform.manufacturing.domain.exceptions;

/**
 * Exception thrown when a batch is not found in the system.
 * @summary
 * This exception is used to indicate that a requested
 * batch could not be located in the manufacturing domain.
 */
public class BatchNotFoundException extends RuntimeException {
    /**
     * Constructor for the exception with a specific message.
     * @param batchId the identifier of the batch that was not found
     */
    public BatchNotFoundException(Long batchId){
        super(String.format("Batch with ID %d not found", batchId));
    }

    /**
     * Constructor for the exception with a specific message.
     * @param batchNumber the number of the batch that was not found
     */
    public BatchNotFoundException(String batchNumber) {
        super(String.format("Batch with number %s not found", batchNumber));
    }

}
