package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.TraceabilityGapCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.TraceabilityGap;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.ITraceabilityGapRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the traceability gaps.
 */
@Service
public class TraceabilityGapCommandServiceImpl implements TraceabilityGapCommandService {

    private final ITraceabilityGapRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the traceability gaps
     */
    public TraceabilityGapCommandServiceImpl(ITraceabilityGapRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<TraceabilityGap, ApplicationError> handle(CreateTraceabilityGapCommand command) {
        try {
            var traceabilityGap = new TraceabilityGap(command);
            return Result.success(repository.save(traceabilityGap));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("TraceabilityGap", e.getMessage()));
        }
    }
}
