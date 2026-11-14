package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class CapaPlanNotFoundException extends RuntimeException {

    public CapaPlanNotFoundException(Long capaPlanId) {
        super("CAPA plan with id " + capaPlanId + " was not found");
    }
}