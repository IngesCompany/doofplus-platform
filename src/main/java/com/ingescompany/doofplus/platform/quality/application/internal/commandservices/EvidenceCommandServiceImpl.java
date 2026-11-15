package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.EvidenceCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.DeleteEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IEvidenceRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the evidence.
 */
@Service
public class EvidenceCommandServiceImpl implements EvidenceCommandService {

    private final IEvidenceRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the evidence
     */
    public EvidenceCommandServiceImpl(IEvidenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Evidence, ApplicationError> handle(CreateEvidenceCommand command) {
        try {
            var evidence = new Evidence(command);
            return Result.success(repository.save(evidence));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Evidence", e.getMessage()));
        }
    }

    @Override
    public Result<Evidence, ApplicationError> handle(UpdateEvidenceCommand command) {
        var found = repository.findById(command.evidenceId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Evidence", String.valueOf(command.evidenceId())));
        }
        try {
            var evidence = found.get();
            evidence.update(command);
            return Result.success(repository.save(evidence));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Evidence", e.getMessage()));
        }
    }

    @Override
    public Result<Long, ApplicationError> handle(DeleteEvidenceCommand command) {
        var found = repository.findById(command.evidenceId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Evidence", String.valueOf(command.evidenceId())));
        }
        if (!found.get().canBeRemoved()) {
            return Result.failure(ApplicationError.conflict("Evidence", "quality.evidence.error.reviewed"));
        }
        repository.deleteById(command.evidenceId());
        return Result.success(command.evidenceId());
    }
}
