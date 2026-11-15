package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the audit findings.
 */
public interface AuditFindingCommandService {

    /**
     * Creates a audit finding.
     *
     * @param command the data of the audit finding
     * @return the created audit finding, or a validation error
     */
    Result<AuditFinding, ApplicationError> handle(CreateAuditFindingCommand command);
}
