package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class EvidenceNotFoundException extends RuntimeException {

    public EvidenceNotFoundException(Long evidenceId) {
        super("Evidence with id " + evidenceId + " was not found");
    }
}