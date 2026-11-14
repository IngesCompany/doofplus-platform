package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve alerts raised for a specific manufacturing batch.
 *
 * @param batchCode batch code
 */
public record GetAllAlertsByBatchCodeQuery(String batchCode) {
    public GetAllAlertsByBatchCodeQuery {
        if (batchCode == null || batchCode.isBlank()) {
            throw new IllegalArgumentException("monitoring.batch.error.code.notBlank");
        }
    }
}
