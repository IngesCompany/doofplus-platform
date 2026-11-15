package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;

/**
 * Command to replace all the data of a evidence.
 *
 * @param evidenceId the identifier of the evidence
 */
public record UpdateEvidenceCommand(
        Long evidenceId,
        String recordCode,
        String fileName,
        String detail,
        EvidenceStatus status) {
}
