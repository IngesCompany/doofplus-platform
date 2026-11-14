package com.ingescompany.doofplus.platform.monitoring.application.queryservices;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Alert} queries.
 */
public interface AlertQueryService {
    List<Alert> handle(GetAllAlertsQuery query);
    Optional<Alert> handle(GetAlertByIdQuery query);
    Optional<Alert> handle(GetAlertByCodeQuery query);
    List<Alert> handle(GetAllAlertsBySensorCodeQuery query);
    List<Alert> handle(GetAllAlertsByBatchCodeQuery query);
}
