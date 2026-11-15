package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

/**
 * Body to update a analytical result. Enums are accepted in kebab-case, PascalCase or UPPER_SNAKE_CASE;
 * missing values take the defaults of the contract.
 */
public record UpdateAnalyticalResultResource(
        String batchCode,
        String sampleCode,
        String reportCode,
        String test,
        String protocol,
        String result,
        String specification,
        String analyst,
        String status,
        String approval) {
}
