package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a deviation. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateDeviationResource(
        String code,
        String title,
        String batchCode,
        String orderCode,
        String summary,
        String classification,
        String containment,
        Integer severity,
        Integer likelihood,
        Integer detectability,
        String status,
        String rootCause,
        String capaCode,
        String reportedBy,
        String reportedAt,
        String owner,
        String dueAt) {
}
