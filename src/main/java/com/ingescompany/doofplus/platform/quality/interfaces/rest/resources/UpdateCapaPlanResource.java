package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to update a capa plan. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record UpdateCapaPlanResource(
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
