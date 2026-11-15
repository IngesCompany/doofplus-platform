package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the audits.
 */
@Service
public class AuditCommandServiceImpl implements AuditCommandService {

    private final IAuditRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the audits
     */
    public AuditCommandServiceImpl(IAuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Audit, ApplicationError> handle(CreateAuditCommand command) {
        try {
            var audit = new Audit(command);
            if (repository.existsByCode(audit.getCode(), null)) {
                return Result.failure(ApplicationError.conflict("Audit", "quality.audit.error.duplicate"));
            }
            return Result.success(repository.save(audit));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Audit", e.getMessage()));
        }
    }
}
