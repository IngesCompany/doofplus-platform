package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Audit;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AuditCommandServiceImpl implements AuditCommandService {

    private final IAuditRepository auditRepository;

    public AuditCommandServiceImpl(IAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Result<Audit, ApplicationError> handle(CreateAuditCommand command) {
        if (auditRepository.existsByCode(command.code())) {
            return Result.failure(
                    ApplicationError.conflict("Audit", "Audit code already exists")
            );
        }

        try {
            Audit audit = new Audit(
                    command.code(),
                    command.title(),
                    command.scope(),
                    command.auditType(),
                    command.auditorId(),
                    command.plannedDate()
            );

            Audit savedAudit = auditRepository.save(audit);
            return Result.success(savedAudit);

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("Audit", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-audit", e.getMessage())
            );
        }
    }
}