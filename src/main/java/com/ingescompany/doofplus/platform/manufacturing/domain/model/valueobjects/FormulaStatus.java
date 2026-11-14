package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Status of a master formula, as the Web App shows it.
 */
public enum FormulaStatus {
    /**
     * Written and not approved yet.
     */
    DRAFT,
    /**
     * Waiting for the approval of Quality.
     */
    PENDING,
    /**
     * Effective version of the product.
     */
    APPROVED,
    /**
     * Replaced by a newer approved version.
     */
    LOCKED
}
