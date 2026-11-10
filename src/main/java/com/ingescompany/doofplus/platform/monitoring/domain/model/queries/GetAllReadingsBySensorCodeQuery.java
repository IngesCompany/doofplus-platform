package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve readings recorded by a specific sensor.
 *
 * @param sensorCode sensor code
 */
public record GetAllReadingsBySensorCodeQuery(String sensorCode) {
    public GetAllReadingsBySensorCodeQuery {
        if (sensorCode == null || sensorCode.isBlank()) {
            throw new IllegalArgumentException("monitoring.sensor.error.code.notBlank");
        }
    }
}
