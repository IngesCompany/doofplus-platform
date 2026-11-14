package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Enumeration representing the Batch Status.
 *
 * <p>
 * The states are the ones the Web App shows for a batch (planned, in progress, on hold,
 * release requested and released).
 * </p>
 */
public enum BatchStatus {
    /**
     * The batch has been planned but not yet started.
     */
    PLANNED,
    /**
     * The batch has been started and is currently in progress.
     */
    IN_PROGRESS,
    /**
     * The batch is currently on hold, indicating that work has been temporarily paused.
     */
    ON_HOLD,
    /**
     * Production finished and the release was requested to QA.
     */
    RELEASE_REQUESTED,
    /**
     * QA released the batch for distribution.
     */
    RELEASED
}
