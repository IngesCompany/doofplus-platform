package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve readings linked to a manufacturing batch.
 *
 * @param batchCode batch code
 */
public record GetAllReadingsByBatchCodeQuery(String batchCode) {
    public GetAllReadingsByBatchCodeQuery {
        if (batchCode == null || batchCode.isBlank()) {
            throw new IllegalArgumentException("monitoring.batch.error.code.notBlank");
        }
    }
}
