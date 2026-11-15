package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the quality documents.
 */
public interface QualityDocumentCommandService {

    /**
     * Creates a quality document.
     *
     * @param command the data of the quality document
     * @return the created quality document, or a validation error
     */
    Result<QualityDocument, ApplicationError> handle(CreateQualityDocumentCommand command);

    /**
     * Replaces the data of a quality document.
     *
     * @param command the identifier and the new data
     * @return the changed quality document, or a not found, validation error
     */
    Result<QualityDocument, ApplicationError> handle(UpdateQualityDocumentCommand command);
}
