package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a capa plan. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateCapaPlanResource(
        String code,
        String title,
        String sourceDeviation,
        String owner,
        String reviewer,
        String rootCause,
        String acceptanceCriterion,
        String effectivenessCheckAt,
        String status) {
}
