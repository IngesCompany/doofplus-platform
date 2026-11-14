package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Status of a production order, as the Web App shows it.
 */
public enum ProductionOrderStatus {
    /**
     * Planned and not approved yet.
     */
    PLANNED,
    /**
     * Approved to run.
     */
    APPROVED,
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
    COMPLETED
}
