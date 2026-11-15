package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to create a traceability gap. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record CreateTraceabilityGapResource(
        String batchCode,
        String product,
        String missingRecord,
        String detail,
        String status) {
}
