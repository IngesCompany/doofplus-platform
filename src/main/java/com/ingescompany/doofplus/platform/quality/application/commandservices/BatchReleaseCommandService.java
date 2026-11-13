package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.BatchRelease;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.ApproveBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.RejectBatchReleaseCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.RequestBatchReleaseCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface BatchReleaseCommandService {

    Result<BatchRelease, ApplicationError> handle(RequestBatchReleaseCommand command);

    Result<BatchRelease, ApplicationError> handle(ApproveBatchReleaseCommand command);

    Result<BatchRelease, ApplicationError> handle(RejectBatchReleaseCommand command);
}