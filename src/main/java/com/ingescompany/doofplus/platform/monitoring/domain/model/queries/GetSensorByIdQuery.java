package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve a sensor by persistent ID.
 *
 * @param id sensor ID
 */
public record GetSensorByIdQuery(Long id) {
    public GetSensorByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("monitoring.sensor.error.id.invalid");
        }
    }
}
