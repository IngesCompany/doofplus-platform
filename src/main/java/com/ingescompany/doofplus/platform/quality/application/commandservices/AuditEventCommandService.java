package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditEventCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the audit events.
 */
public interface AuditEventCommandService {

    /**
     * Creates a audit event.
     *
     * @param command the data of the audit event
     * @return the created audit event, or a validation error
     */
    Result<AuditEvent, ApplicationError> handle(CreateAuditEventCommand command);
}
