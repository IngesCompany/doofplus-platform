package com.ingescompany.doofplus.platform.monitoring.domain.exceptions;

public class AlertNotFoundException extends RuntimeException {
    public AlertNotFoundException(Long id) {
        super("Alert with id " + id + " not found");
    }

    public AlertNotFoundException(String code) {
        super("Alert with code " + code + " not found");
    }
}
