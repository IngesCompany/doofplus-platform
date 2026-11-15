package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.DeviationCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the deviations.
 */
@Service
public class DeviationCommandServiceImpl implements DeviationCommandService {

    private final IDeviationRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the deviations
     */
    public DeviationCommandServiceImpl(IDeviationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Deviation, ApplicationError> handle(CreateDeviationCommand command) {
        try {
            var deviation = new Deviation(command);
            if (repository.existsByCode(deviation.getCode(), null)) {
                return Result.failure(ApplicationError.conflict("Deviation", "quality.deviation.error.duplicate"));
            }
            return Result.success(repository.save(deviation));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Deviation", e.getMessage()));
        }
    }

    @Override
    public Result<Deviation, ApplicationError> handle(UpdateDeviationCommand command) {
        var found = repository.findById(command.deviationId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Deviation", String.valueOf(command.deviationId())));
        }
        try {
            var deviation = found.get();
            deviation.update(command);
            if (repository.existsByCode(deviation.getCode(), deviation.getId())) {
                return Result.failure(ApplicationError.conflict("Deviation", "quality.deviation.error.duplicate"));
            }
            return Result.success(repository.save(deviation));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Deviation", e.getMessage()));
        }
    }
}
