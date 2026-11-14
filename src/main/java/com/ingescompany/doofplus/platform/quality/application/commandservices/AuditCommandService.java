package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface AuditCommandService {
    Result<Audit, ApplicationError> handle(CreateAuditCommand command);
}