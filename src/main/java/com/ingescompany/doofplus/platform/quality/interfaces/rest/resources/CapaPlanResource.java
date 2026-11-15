package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Capa plan answered by the API. Enums in UPPER_SNAKE_CASE, dates in yyyy-MM-dd and date-times in ISO 8601 UTC.
 */
public record CapaPlanResource(
        Long id,
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
