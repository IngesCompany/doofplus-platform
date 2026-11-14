package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class DeviationNotFoundException extends RuntimeException {

    public DeviationNotFoundException(Long deviationId) {
        super("Deviation with id " + deviationId + " was not found");
    }
}