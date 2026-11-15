package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;

/**
 * Command to create a evidence. Null values take the defaults of the contract.
 */
public record CreateEvidenceCommand(
        String recordCode,
        String fileName,
        String detail,
        EvidenceStatus status) {
}
