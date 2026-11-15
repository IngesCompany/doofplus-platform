package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaActionCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaActionRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the capa actions.
 */
@Service
public class CapaActionCommandServiceImpl implements CapaActionCommandService {

    private final ICapaActionRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the capa actions
     */
    public CapaActionCommandServiceImpl(ICapaActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<CapaAction, ApplicationError> handle(CreateCapaActionCommand command) {
        try {
            var capaAction = new CapaAction(command);
            return Result.success(repository.save(capaAction));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("CapaAction", e.getMessage()));
        }
    }

    @Override
    public Result<CapaAction, ApplicationError> handle(UpdateCapaActionCommand command) {
        var found = repository.findById(command.capaActionId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("CapaAction", String.valueOf(command.capaActionId())));
        }
        try {
            var capaAction = found.get();
            capaAction.update(command);
            return Result.success(repository.save(capaAction));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("CapaAction", e.getMessage()));
        }
    }
}
