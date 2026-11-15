package com.ingescompany.doofplus.platform.quality.application.commandservices;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.Evidence;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.DeleteEvidenceCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Command service of the evidence.
 */
public interface EvidenceCommandService {

    /**
     * Creates a evidence.
     *
     * @param command the data of the evidence
     * @return the created evidence, or a validation error
     */
    Result<Evidence, ApplicationError> handle(CreateEvidenceCommand command);

    /**
     * Replaces the data of a evidence.
     *
     * @param command the identifier and the new data
     * @return the changed evidence, or a not found, validation error
     */
    Result<Evidence, ApplicationError> handle(UpdateEvidenceCommand command);

    /**
     * Removes a evidence.
     *
     * @param command the identifier
     * @return the identifier removed, or a not found or conflict error
     */
    Result<Long, ApplicationError> handle(DeleteEvidenceCommand command);
}
