package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve a telemetry reading by persistent ID.
 *
 * @param id reading ID
 */
public record GetReadingByIdQuery(Long id) {
    public GetReadingByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("monitoring.reading.error.id.invalid");
        }
    }
}
