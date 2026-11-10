package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface AuditFindingCommandService {
    Result<AuditFinding, ApplicationError> handle(CreateAuditFindingCommand command);
}