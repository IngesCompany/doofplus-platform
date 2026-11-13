package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class AuditNotFoundException extends RuntimeException {

    public AuditNotFoundException(Long auditId) {
        super("Audit with id " + auditId + " was not found");
    }
}