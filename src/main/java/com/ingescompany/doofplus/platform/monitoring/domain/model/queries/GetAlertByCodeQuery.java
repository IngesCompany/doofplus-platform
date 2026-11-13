package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;

/**
 * Query to retrieve a monitoring alert by alert code.
 *
 * @param code alert code
 */
public record GetAlertByCodeQuery(AlertCode code) {
    public GetAlertByCodeQuery {
        if (code == null) {
            throw new IllegalArgumentException("monitoring.alert.error.code.notBlank");
        }
    }
}
