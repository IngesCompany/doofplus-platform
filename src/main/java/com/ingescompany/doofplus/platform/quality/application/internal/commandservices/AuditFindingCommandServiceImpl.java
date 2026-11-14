package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.AuditFindingCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.AuditFinding;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IAuditFindingRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class AuditFindingCommandServiceImpl implements AuditFindingCommandService {

    private final IAuditFindingRepository auditFindingRepository;

    public AuditFindingCommandServiceImpl(IAuditFindingRepository auditFindingRepository) {
        this.auditFindingRepository = auditFindingRepository;
    }

    @Override
    public Result<AuditFinding, ApplicationError> handle(CreateAuditFindingCommand command) {
        try {
            AuditFinding auditFinding = new AuditFinding(
                    command.auditId(),
                    command.title(),
                    command.description(),
                    command.severity(),
                    command.relatedDeviationId()
            );

            AuditFinding savedAuditFinding = auditFindingRepository.save(auditFinding);
            return Result.success(savedAuditFinding);

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("AuditFinding", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-audit-finding", e.getMessage())
            );
        }
    }
}