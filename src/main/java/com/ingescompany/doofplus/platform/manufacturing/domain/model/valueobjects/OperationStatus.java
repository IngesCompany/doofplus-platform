package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Status of an operation of a production order, as the Web App shows it.
 */
public enum OperationStatus {
    /**
     * Not started.
     */
    NOT_STARTED,
    /**
     * Running.
     */
    IN_PROGRESS,
    /**
     * Paused.
     */
    ON_HOLD,
    /**
     * Finished.
     */
    COMPLETE
}
