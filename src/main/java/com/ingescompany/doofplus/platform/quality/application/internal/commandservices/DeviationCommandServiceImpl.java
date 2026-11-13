package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.DeviationCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Deviation;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateDeviationCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IDeviationRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class DeviationCommandServiceImpl implements DeviationCommandService {

    private final IDeviationRepository deviationRepository;

    public DeviationCommandServiceImpl(IDeviationRepository deviationRepository) {
        this.deviationRepository = deviationRepository;
    }

    @Override
    public Result<Deviation, ApplicationError> handle(CreateDeviationCommand command) {
        if (deviationRepository.existsByCode(command.code())) {
            return Result.failure(
                    ApplicationError.conflict("Deviation", "Deviation code already exists")
            );
        }

        try {
            Deviation deviation = new Deviation(
                    command.code(),
                    command.title(),
                    command.description(),
                    command.severity(),
                    command.batchId(),
                    command.assignedTo()
            );

            return Result.success(deviationRepository.save(deviation));

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("Deviation", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-deviation", e.getMessage())
            );
        }
    }
}