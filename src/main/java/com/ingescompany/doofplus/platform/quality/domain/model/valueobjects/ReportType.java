package com.ingescompany.doofplus.platform.quality.domain.model.valueobjects;

/**
 * Report type values of the Quality and Compliance bounded context.
 * They are answered in UPPER_SNAKE_CASE and accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE.
 */
public enum ReportType {
    BATCH_DOSSIER,
    QUALITY_REVIEW,
    CAPA_SUMMARY,
    AUDIT_EVIDENCE
}
