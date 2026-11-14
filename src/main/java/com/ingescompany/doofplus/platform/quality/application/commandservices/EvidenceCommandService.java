package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface EvidenceCommandService {
    Result<Evidence, ApplicationError> handle(CreateEvidenceCommand command);
}