package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.EvidenceCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class EvidenceCommandServiceImpl implements EvidenceCommandService {

    private final IEvidenceRepository evidenceRepository;

    public EvidenceCommandServiceImpl(IEvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    public Result<Evidence, ApplicationError> handle(CreateEvidenceCommand command) {
        try {
            Evidence evidence = new Evidence(
                    command.title(),
                    command.description(),
                    command.type(),
                    command.fileReference(),
                    command.deviationId(),
                    command.documentId(),
                    command.uploadedBy()
            );

            return Result.success(evidenceRepository.save(evidence));

        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("Evidence", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("create-evidence", e.getMessage())
            );
        }
    }
}