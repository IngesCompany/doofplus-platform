package com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects;

/**
 * Kind of line of a master formula.
 */
public enum FormulaItemKind {
    /**
     * Raw material of the formula.
     */
    COMPONENT,
    /**
     * Process parameter with its target and tolerance.
     */
    PARAMETER
}
