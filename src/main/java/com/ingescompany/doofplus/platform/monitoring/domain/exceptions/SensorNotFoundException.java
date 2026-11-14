package com.ingescompany.doofplus.platform.monitoring.domain.exceptions;

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(Long id) {
        super("Sensor with id " + id + " not found");
    }

    public SensorNotFoundException(String code) {
        super("Sensor with code " + code + " not found");
    }
}
