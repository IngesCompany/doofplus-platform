package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AnalyticalResultCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AnalyticalResult;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAnalyticalResultRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AnalyticalResultCommandServiceImpl implements AnalyticalResultCommandService {

    private final IAnalyticalResultRepository analyticalResultRepository;

    public AnalyticalResultCommandServiceImpl(IAnalyticalResultRepository analyticalResultRepository) {
        this.analyticalResultRepository = analyticalResultRepository;
    }

    @Override
    public Result<AnalyticalResult, ApplicationError> handle(CreateAnalyticalResultCommand command) {
        try {
            AnalyticalResult analyticalResult = new AnalyticalResult(
                    command.batchId(),
                    command.testName(),
                    command.parameter(),
                    command.measuredValue(),
                    command.minimumValue(),
                    command.maximumValue(),
                    command.unit(),
                    command.testedAt()
            );

            AnalyticalResult savedResult = analyticalResultRepository.save(analyticalResult);
            return Result.success(savedResult);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("AnalyticalResult", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-analytical-result", e.getMessage()));
        }
    }
}