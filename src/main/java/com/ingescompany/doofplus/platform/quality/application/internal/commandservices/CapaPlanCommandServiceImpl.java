package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaPlanCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the capa plans.
 */
@Service
public class CapaPlanCommandServiceImpl implements CapaPlanCommandService {

    private final ICapaPlanRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the capa plans
     */
    public CapaPlanCommandServiceImpl(ICapaPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<CapaPlan, ApplicationError> handle(CreateCapaPlanCommand command) {
        try {
            var capaPlan = new CapaPlan(command);
            if (repository.existsByCode(capaPlan.getCode(), null)) {
                return Result.failure(ApplicationError.conflict("CapaPlan", "quality.capaPlan.error.duplicate"));
            }
            return Result.success(repository.save(capaPlan));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("CapaPlan", e.getMessage()));
        }
    }

    @Override
    public Result<CapaPlan, ApplicationError> handle(UpdateCapaPlanCommand command) {
        var found = repository.findById(command.capaPlanId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("CapaPlan", String.valueOf(command.capaPlanId())));
        }
        try {
            var capaPlan = found.get();
            capaPlan.update(command);
            if (repository.existsByCode(capaPlan.getCode(), capaPlan.getId())) {
                return Result.failure(ApplicationError.conflict("CapaPlan", "quality.capaPlan.error.duplicate"));
            }
            return Result.success(repository.save(capaPlan));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("CapaPlan", e.getMessage()));
        }
    }
}
