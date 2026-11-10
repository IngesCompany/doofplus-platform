package com.ingescompany.doofplus.platform.quality.domain.exceptions;

public class RegulatoryReportNotFoundException extends RuntimeException {

    public RegulatoryReportNotFoundException(Long reportId) {
        super("Regulatory report with id " + reportId + " was not found");
    }
}