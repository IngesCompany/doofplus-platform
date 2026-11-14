package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve alerts raised for a specific sensor.
 *
 * @param sensorCode sensor code
 */
public record GetAllAlertsBySensorCodeQuery(String sensorCode) {
    public GetAllAlertsBySensorCodeQuery {
        if (sensorCode == null || sensorCode.isBlank()) {
            throw new IllegalArgumentException("monitoring.sensor.error.code.notBlank");
        }
    }
}
