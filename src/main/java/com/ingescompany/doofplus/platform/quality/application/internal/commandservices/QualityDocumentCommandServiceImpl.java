package com.ingescompany.doofplus.platform.quality.application.internal.commandservices;

import com.ingescompany.doofplus.platform.quality.application.commandservices.QualityDocumentCommandService;
import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.repositories.IQualityDocumentRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QualityDocumentCommandServiceImpl implements QualityDocumentCommandService {

    private final IQualityDocumentRepository qualityDocumentRepository;

    public QualityDocumentCommandServiceImpl(IQualityDocumentRepository qualityDocumentRepository) {
        this.qualityDocumentRepository = qualityDocumentRepository;
    }

    @Override
    public Result<QualityDocument, ApplicationError> handle(CreateQualityDocumentCommand command) {
        if (qualityDocumentRepository.existsByCode(command.code())) {
            return Result.failure(
                    ApplicationError.conflict(
                            "QualityDocument",
                            "Document with code " + command.code() + " already exists"
                    )
            );
        }

        var document = new QualityDocument(
                command.code(),
                command.title(),
                command.description(),
                command.documentType(),
                command.fileReference(),
                command.version(),
                command.ownerId()
        );

        var savedDocument = qualityDocumentRepository.save(document);

        return Result.success(savedDocument);
    }
}