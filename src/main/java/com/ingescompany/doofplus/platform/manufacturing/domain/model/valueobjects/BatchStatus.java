package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Enumeration representing the Batch Status.
 *
 * <p>
 * This enumeration is used to define the various states a batch can be in during its lifecycle.
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
     * The batch has been finished.
     */
    FINISHED,
    /**
     * The batch has been closed.
     */
    CLOSED,
    /**
     * A request for release has been submitted for the batch.
     */
    RELEASE_REQUESTED,
    /**
     * The batch has been released.
     */
    RELEASED,
    /**
     * The batch has been rejected.
     */
    REJECTED
}
