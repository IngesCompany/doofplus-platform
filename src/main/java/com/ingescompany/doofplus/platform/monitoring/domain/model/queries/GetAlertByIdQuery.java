package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve a monitoring alert by persistent ID.
 *
 * @param id alert ID
 */
public record GetAlertByIdQuery(Long id) {
    public GetAlertByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("monitoring.alert.error.id.invalid");
        }
    }
}
