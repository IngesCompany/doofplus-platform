package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the audits.
 */
public interface AuditCommandService {

    /**
     * Creates a audit.
     *
     * @param command the data of the audit
     * @return the created audit, or a validation or conflict error
     */
    Result<Audit, ApplicationError> handle(CreateAuditCommand command);
}
