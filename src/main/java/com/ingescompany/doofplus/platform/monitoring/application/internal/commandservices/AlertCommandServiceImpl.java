package com.ingescompany.doofplus.platform.monitoring.application.internal.commandservices;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.AlertCommandService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Alert;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.UpdateAlertCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.AlertRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link AlertCommandService}.
 */
@Service
@Transactional
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository alertRepository;

    public AlertCommandServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Result<Alert, ApplicationError> handle(CreateAlertCommand command) {
        if (alertRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("Alert", "Alert with code " + command.code().value() + " already exists"));
        }
        var alert = new Alert(command);
        var savedAlert = alertRepository.save(alert);
        return Result.success(savedAlert);
    }

    @Override
    public Result<Alert, ApplicationError> handle(UpdateAlertCommand command) {
        var existingAlertOpt = alertRepository.findById(command.id());
        if (existingAlertOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Alert", command.id().toString()));
        }
        var alert = existingAlertOpt.get();
        if (command.code() != null && !command.code().equals(alert.getCode()) && alertRepository.existsByCode(command.code())) {
            return Result.failure(ApplicationError.conflict("Alert", "Alert with code " + command.code().value() + " already exists"));
        }
        alert.update(command);
        var updatedAlert = alertRepository.save(alert);
        return Result.success(updatedAlert);
    }
}
