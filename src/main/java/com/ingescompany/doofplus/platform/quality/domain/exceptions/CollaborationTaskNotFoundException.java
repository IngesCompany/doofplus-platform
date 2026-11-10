package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class CollaborationTaskNotFoundException extends RuntimeException {

    public CollaborationTaskNotFoundException(Long taskId) {
        super("Collaboration task with id " + taskId + " was not found");
    }
}