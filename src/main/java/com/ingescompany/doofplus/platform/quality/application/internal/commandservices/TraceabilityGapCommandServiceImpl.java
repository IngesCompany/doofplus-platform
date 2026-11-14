package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TraceabilityGapCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class TraceabilityGapCommandServiceImpl implements TraceabilityGapCommandService {

    private final ITraceabilityGapRepository traceabilityGapRepository;

    public TraceabilityGapCommandServiceImpl(ITraceabilityGapRepository traceabilityGapRepository) {
        this.traceabilityGapRepository = traceabilityGapRepository;
    }

    @Override
    public Result<TraceabilityGap, ApplicationError> handle(CreateTraceabilityGapCommand command) {
        try {
            TraceabilityGap traceabilityGap = new TraceabilityGap(
                    command.batchId(),
                    command.description(),
                    command.missingReference(),
                    command.affectedProcess()
            );

            return Result.success(traceabilityGapRepository.save(traceabilityGap));
        } catch (IllegalArgumentException exception) {
            return Result.failure(
                    ApplicationError.validationError("traceability gap", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("creating traceability gap", exception.getMessage())
            );
        }
    }
}