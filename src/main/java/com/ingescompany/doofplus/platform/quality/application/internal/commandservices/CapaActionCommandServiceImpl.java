package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaActionCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaAction;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaActionCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaActionRepository;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class CapaActionCommandServiceImpl implements CapaActionCommandService {

    private final ICapaActionRepository capaActionRepository;
    private final ICapaPlanRepository capaPlanRepository;

    public CapaActionCommandServiceImpl(
            ICapaActionRepository capaActionRepository,
            ICapaPlanRepository capaPlanRepository
    ) {
        this.capaActionRepository = capaActionRepository;
        this.capaPlanRepository = capaPlanRepository;
    }

    @Override
    public Result<CapaAction, ApplicationError> handle(CreateCapaActionCommand command) {
        if (capaPlanRepository.findById(command.capaPlanId()).isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("CapaPlan", command.capaPlanId().toString())
            );
        }

        try {
            CapaAction capaAction = new CapaAction(
                    command.capaPlanId(),
                    command.title(),
                    command.description(),
                    command.assignedTo().toString(),
                    command.dueDate()
            );

            return Result.success(capaActionRepository.save(capaAction));

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("CapaAction", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-capa-action", e.getMessage())
            );
        }
    }
}