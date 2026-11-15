package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AnalyticalResultCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAnalyticalResultRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the analytical results.
 */
@Service
public class AnalyticalResultCommandServiceImpl implements AnalyticalResultCommandService {

    private final IAnalyticalResultRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the analytical results
     */
    public AnalyticalResultCommandServiceImpl(IAnalyticalResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<AnalyticalResult, ApplicationError> handle(CreateAnalyticalResultCommand command) {
        try {
            var analyticalResult = new AnalyticalResult(command);
            return Result.success(repository.save(analyticalResult));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("AnalyticalResult", e.getMessage()));
        }
    }

    @Override
    public Result<AnalyticalResult, ApplicationError> handle(UpdateAnalyticalResultCommand command) {
        var found = repository.findById(command.analyticalResultId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("AnalyticalResult", String.valueOf(command.analyticalResultId())));
        }
        try {
            var analyticalResult = found.get();
            analyticalResult.update(command);
            return Result.success(repository.save(analyticalResult));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("AnalyticalResult", e.getMessage()));
        }
    }
}
