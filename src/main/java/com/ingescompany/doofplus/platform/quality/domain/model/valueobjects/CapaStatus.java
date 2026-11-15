package com.ingescompany.doofplus.platform.quality.domain.model.valueobjects;

/**
 * Capa status values of the Quality and Compliance bounded context.
 * They are answered in UPPER_SNAKE_CASE and accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE.
 */
public enum CapaStatus {
    DRAFT,
    IN_PROGRESS,
    IN_APPROVAL,
    CLOSED
}
