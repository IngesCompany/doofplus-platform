package com.ingescompany.doofplus.platform.monitoring.domain.exceptions;

public class ReadingNotFoundException extends RuntimeException {
    public ReadingNotFoundException(Long id) {
        super("Reading with id " + id + " not found");
    }
}
