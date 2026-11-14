package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Status of a raw material lot, as the Web App shows it.
 */
public enum RawMaterialLotStatus {
    /**
     * Received and kept apart.
     */
    QUARANTINE,
    /**
     * Waiting for the QC sample.
     */
    SAMPLING,
    /**
     * Under QC inspection.
     */
    INSPECTION,
    /**
     * Released for use.
     */
    APPROVED,
    /**
     * Rejected by QC.
     */
    REJECTED
}
