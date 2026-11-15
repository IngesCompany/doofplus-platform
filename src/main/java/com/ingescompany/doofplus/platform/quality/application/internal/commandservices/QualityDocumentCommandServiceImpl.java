package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.QualityDocumentCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Implementation of the command service of the quality documents.
 */
@Service
public class QualityDocumentCommandServiceImpl implements QualityDocumentCommandService {

    private final IQualityDocumentRepository repository;

    /**
     * Creates the service.
     *
     * @param repository the repository of the quality documents
     */
    public QualityDocumentCommandServiceImpl(IQualityDocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<QualityDocument, ApplicationError> handle(CreateQualityDocumentCommand command) {
        try {
            var qualityDocument = new QualityDocument(command);
            return Result.success(repository.save(qualityDocument));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("QualityDocument", e.getMessage()));
        }
    }

    @Override
    public Result<QualityDocument, ApplicationError> handle(UpdateQualityDocumentCommand command) {
        var found = repository.findById(command.qualityDocumentId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("QualityDocument", String.valueOf(command.qualityDocumentId())));
        }
        try {
            var qualityDocument = found.get();
            qualityDocument.update(command);
            return Result.success(repository.save(qualityDocument));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("QualityDocument", e.getMessage()));
        }
    }
}
