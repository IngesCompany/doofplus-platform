package com.ingescompany.doofplus.platform.monitoring.application.internal.queryservices;

import com.ingescompany.doofplus.platform.monitoring.application.queryservices.AlertQueryService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.queries.*;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link AlertQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.id());
    }

    @Override
    public Optional<Alert> handle(GetAlertByCodeQuery query) {
        return alertRepository.findByCode(query.code());
    }

    @Override
    public List<Alert> handle(GetAllAlertsBySensorCodeQuery query) {
        return alertRepository.findAllBySensorCode(query.sensorCode());
    }

    @Override
    public List<Alert> handle(GetAllAlertsByBatchCodeQuery query) {
        return alertRepository.findAllByBatchCode(query.batchCode());
    }
}
