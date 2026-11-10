package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class QualityDocumentNotFoundException extends RuntimeException {

    public QualityDocumentNotFoundException(Long documentId) {
        super("Quality document with id " + documentId + " was not found");
    }
}