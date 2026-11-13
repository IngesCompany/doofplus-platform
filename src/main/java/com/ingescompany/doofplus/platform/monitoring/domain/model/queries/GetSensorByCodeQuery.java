package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;

/**
 * Query to retrieve a sensor by sensor code.
 *
 * @param code sensor code
 */
public record GetSensorByCodeQuery(SensorCode code) {
    public GetSensorByCodeQuery {
        if (code == null) {
            throw new IllegalArgumentException("monitoring.sensor.error.code.notBlank");
        }
    }
}
