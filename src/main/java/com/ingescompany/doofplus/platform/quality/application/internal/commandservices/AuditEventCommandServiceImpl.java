package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditEventCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditEvent;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditEventCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditEventRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the audit events.
 */
@Service
public class AuditEventCommandServiceImpl implements AuditEventCommandService {

    private final IAuditEventRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the audit events
     */
    public AuditEventCommandServiceImpl(IAuditEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<AuditEvent, ApplicationError> handle(CreateAuditEventCommand command) {
        try {
            var auditEvent = new AuditEvent(command);
            return Result.success(repository.save(auditEvent));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("AuditEvent", e.getMessage()));
        }
    }
}
