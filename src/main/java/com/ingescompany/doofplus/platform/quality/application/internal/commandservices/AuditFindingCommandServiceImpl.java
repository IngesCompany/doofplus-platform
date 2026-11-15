package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditFindingCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditFindingRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the audit findings.
 */
@Service
public class AuditFindingCommandServiceImpl implements AuditFindingCommandService {

    private final IAuditFindingRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the audit findings
     */
    public AuditFindingCommandServiceImpl(IAuditFindingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<AuditFinding, ApplicationError> handle(CreateAuditFindingCommand command) {
        try {
            var auditFinding = new AuditFinding(command);
            return Result.success(repository.save(auditFinding));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("AuditFinding", e.getMessage()));
        }
    }
}
