package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Status of the QC sampling of a raw material lot.
 */
public enum SamplingStatus {
    /**
     * Not requested yet.
     */
    PENDING,
    /**
     * Requested to QC.
     */
    REQUESTED,
    /**
     * Sample taken.
     */
    DONE
}
