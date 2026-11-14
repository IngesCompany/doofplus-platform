package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.QualityDocument;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateQualityDocumentCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface QualityDocumentCommandService {

    Result<QualityDocument, ApplicationError> handle(CreateQualityDocumentCommand command);
}