package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.CapaPlanCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.CapaPlan;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateCapaPlanCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ICapaPlanRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class CapaPlanCommandServiceImpl implements CapaPlanCommandService {

    private final ICapaPlanRepository capaPlanRepository;

    public CapaPlanCommandServiceImpl(ICapaPlanRepository capaPlanRepository) {
        this.capaPlanRepository = capaPlanRepository;
    }

    @Override
    public Result<CapaPlan, ApplicationError> handle(CreateCapaPlanCommand command) {
        try {
            CapaPlan capaPlan = new CapaPlan(
                    command.deviationId(),
                    command.title(),
                    command.description(),
                    command.dueDate(),
                    command.responsible().toString()
            );

            return Result.success(capaPlanRepository.save(capaPlan));

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("CapaPlan", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-capa-plan", e.getMessage())
            );
        }
    }
}