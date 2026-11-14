package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class BatchReleaseNotFoundException extends RuntimeException {

    public BatchReleaseNotFoundException(Long releaseId) {
        super("Batch release with id " + releaseId + " was not found");
    }
}